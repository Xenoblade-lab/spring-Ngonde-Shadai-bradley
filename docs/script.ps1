#Requires -Version 5.1
<#
.SYNOPSIS
    Convertit les fichiers Markdown du dossier docs en HTML puis PDF (Pandoc + Chrome headless).

.DESCRIPTION
    Étape 1 : Pandoc  →  HTML standalone (+ _doc_pdf_style.html)
    Étape 2 : Chrome headless  →  PDF (ou wkhtmltopdf via Pandoc si installé)

.EXAMPLE
    cd docs
    .\script.ps1

.EXAMPLE
    .\script.ps1 -Files "PROJET-SPRING-PLAN.md"
#>
param(
    [string[]]$Files = @(
        "PROJET-SPRING-PLAN.md"
    )
)

$ErrorActionPreference = "Stop"

$d = $PSScriptRoot
Set-Location $d

$outDir = Join-Path $d "out"
New-Item -ItemType Directory -Force -Path $outDir | Out-Null

$styleFile = Join-Path $d "_doc_pdf_style.html"
if (-not (Test-Path $styleFile)) {
    Write-Host "Fichier CSS introuvable : $styleFile" -ForegroundColor Red
    exit 1
}

$p = Join-Path $env:LOCALAPPDATA "Pandoc\pandoc.exe"
if (-not (Test-Path $p)) {
    $pandocCmd = Get-Command pandoc -ErrorAction SilentlyContinue
    if (-not $pandocCmd) {
        Write-Host "Pandoc introuvable. Installez-le : https://pandoc.org/installing.html" -ForegroundColor Red
        exit 1
    }
    $p = $pandocCmd.Source
}

function Get-BrowserPath {
    $candidates = @(
        "$env:ProgramFiles\Google\Chrome\Application\chrome.exe",
        "${env:ProgramFiles(x86)}\Google\Chrome\Application\chrome.exe",
        "${env:ProgramFiles(x86)}\Microsoft\Edge\Application\msedge.exe",
        "$env:ProgramFiles\Microsoft\Edge\Application\msedge.exe"
    )
    foreach ($candidate in $candidates) {
        if (Test-Path $candidate) { return $candidate }
    }
    return $null
}

function Convert-MarkdownToHtml {
    param(
        [string]$InputPath,
        [string]$HtmlPath,
        [string]$Title
    )

    Write-Host "  Pandoc : $([IO.Path]::GetFileName($InputPath)) -> $([IO.Path]::GetFileName($HtmlPath))" -ForegroundColor Cyan
    & $p $InputPath `
        -o $HtmlPath `
        -s `
        --from markdown+raw_html `
        --metadata "title=$Title" `
        -H $styleFile

    if ($LASTEXITCODE -ne 0 -or -not (Test-Path $HtmlPath)) {
        throw "Echec conversion HTML pour $InputPath"
    }
}

function Convert-HtmlToPdf-Pandoc {
    param(
        [string]$HtmlPath,
        [string]$PdfPath
    )

    $engine = (Get-Command wkhtmltopdf -ErrorAction SilentlyContinue).Source
    if (-not $engine) {
        return $false
    }

    & $p $HtmlPath -f html -t pdf --pdf-engine=$engine -o $PdfPath
    if ($LASTEXITCODE -ne 0) {
        throw "Echec conversion PDF (wkhtmltopdf) pour $HtmlPath"
    }
    return $true
}

function Convert-HtmlToPdf-Browser {
    param(
        [string]$HtmlPath,
        [string]$PdfPath
    )

    $browser = Get-BrowserPath
    if (-not $browser) {
        throw "Aucun navigateur headless trouve (Chrome ou Edge)."
    }

    if (Test-Path $PdfPath) { Remove-Item $PdfPath -Force }

    $htmlUri = [System.Uri]::new((Resolve-Path $HtmlPath).Path).AbsoluteUri
    $sizeKb = (Get-Item $HtmlPath).Length / 1KB
    $budget = if ($sizeKb -gt 800) { 60000 } elseif ($sizeKb -gt 200) { 30000 } else { 20000 }

    Write-Host "  Navigateur : $([IO.Path]::GetFileName($PdfPath))" -ForegroundColor Cyan

    $prevEap = $ErrorActionPreference
    $ErrorActionPreference = "Continue"
    try {
        & $browser `
            --headless=new `
            --disable-gpu `
            --no-pdf-header-footer `
            --run-all-compositor-stages-before-draw `
            --virtual-time-budget=$budget `
            "--print-to-pdf=$PdfPath" `
            $htmlUri 2>&1 | Out-Null
    } finally {
        $ErrorActionPreference = $prevEap
    }

    $deadline = (Get-Date).AddSeconds(30)
    while (-not (Test-Path $PdfPath) -and (Get-Date) -lt $deadline) {
        Start-Sleep -Milliseconds 250
    }

    if (-not (Test-Path $PdfPath)) {
        throw "Echec generation PDF : $PdfPath"
    }
}

function Convert-Document {
    param(
        [string]$FileName,
        [string]$Title
    )

    $inputPath = Join-Path $d $FileName
    if (-not (Test-Path $inputPath)) {
        Write-Warning "Fichier ignore (introuvable) : $FileName"
        return
    }

    $base = [IO.Path]::GetFileNameWithoutExtension($FileName)
    $htmlPath = Join-Path $outDir "$base.html"
    $pdfPath = Join-Path $outDir "$base.pdf"

    Write-Host ""
    Write-Host "=== $FileName ===" -ForegroundColor White

    Write-Host "  [1/2] Markdown -> HTML"
    Convert-MarkdownToHtml -InputPath $inputPath -HtmlPath $htmlPath -Title $Title

    Write-Host "  [2/2] HTML -> PDF"
    $usedPandoc = Convert-HtmlToPdf-Pandoc -HtmlPath $htmlPath -PdfPath $pdfPath
    if (-not $usedPandoc) {
        Write-Host "        (wkhtmltopdf absent -> Chrome/Edge headless)" -ForegroundColor DarkYellow
        Convert-HtmlToPdf-Browser -HtmlPath $htmlPath -PdfPath $pdfPath
    }

    $item = Get-Item $pdfPath
    $kb = [math]::Round($item.Length / 1KB, 1)
    Write-Host "  OK  $($item.Name) ($kb KB)" -ForegroundColor Green
    Write-Host "      HTML : $htmlPath" -ForegroundColor DarkGray
    Write-Host "      PDF  : $pdfPath" -ForegroundColor DarkGray
}

$titles = @{
    "PROJET-SPRING-PLAN.md" = "Projet Spring - Gestion Candidats et Formations"
}

Write-Host "Conversion Markdown -> HTML -> PDF" -ForegroundColor Cyan
Write-Host "Dossier : $d"
Write-Host "Sortie  : $outDir"

foreach ($file in $Files) {
    $title = if ($titles.ContainsKey($file)) {
        $titles[$file]
    } else {
        [IO.Path]::GetFileNameWithoutExtension($file)
    }
    Convert-Document -FileName $file -Title $title
}

Write-Host ""
Write-Host "Termine." -ForegroundColor Green
