#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

printf '== ReportaYa: validation ==\n'
bash ./validate-ui.sh

SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-}}"
if [[ -z "$SDK_ROOT" || ! -d "$SDK_ROOT" ]]; then
  printf 'ERROR: Android SDK no encontrado. Define ANDROID_SDK_ROOT o ANDROID_HOME.\n' >&2
  printf 'Ejemplo: export ANDROID_SDK_ROOT="$HOME/Android/Sdk"\n' >&2
  exit 2
fi

if [[ ! -f "$SDK_ROOT/platforms/android-37/android.jar" && ! -f "$SDK_ROOT/platforms/android-37.0/android.jar" ]]; then
  printf 'ERROR: falta Android Platform API 37 en %s/platforms.\n' "$SDK_ROOT" >&2
  printf 'Instálala desde Android Studio > SDK Manager y vuelve a ejecutar este script.\n' >&2
  exit 3
fi

printf '== Unit tests ==\n'
./gradlew --no-daemon testDebugUnitTest

printf '== Assemble debug APK ==\n'
./gradlew --no-daemon assembleDebug

APK="$ROOT_DIR/app/build/outputs/apk/debug/app-debug.apk"
if [[ ! -f "$APK" ]]; then
  printf 'ERROR: Gradle terminó sin producir %s\n' "$APK" >&2
  exit 4
fi

OUT_DIR="$ROOT_DIR/../../deliverables"
mkdir -p "$OUT_DIR"
cp "$APK" "$OUT_DIR/ReportaYa-debug.apk"

printf '\nOK: APK generado en:\n%s\n' "$OUT_DIR/ReportaYa-debug.apk"
