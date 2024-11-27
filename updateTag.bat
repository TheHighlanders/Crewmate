@echo off
set /p TAG="Enter tag to update (e.g. v1.0.0): "

git tag -d %TAG%
git push origin -d %TAG%
git tag %TAG%
git push origin %TAG%
