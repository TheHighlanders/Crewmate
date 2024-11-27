@echo off
set /p VERSION="Enter version to update (e.g. 1.0.0): "
set TAG=%VERSION%

if not "%VERSION:~0,1%"=="v" set TAG=v%VERSION%

git tag -d %TAG%
git push origin :refs/tags/%TAG%
git tag %TAG%
git push origin %TAG%