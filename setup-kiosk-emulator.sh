#!/usr/bin/env bash
set -euo pipefail

readonly PACKAGE="com.example.montasch"
readonly ADMIN_RECEIVER="$PACKAGE/.KioskDeviceAdminReceiver"
readonly ACTIVITY="$PACKAGE/.MainActivity"

if ! command -v adb >/dev/null 2>&1; then
    echo "adb не знайдено. Запустіть скрипт із термінала Android Studio або додайте platform-tools до PATH." >&2
    exit 1
fi

mapfile -t devices < <(adb devices | awk 'NR > 1 && $2 == "device" { print $1 }')
if [[ ${#devices[@]} -ne 1 ]]; then
    echo "Потрібен рівно один запущений та розблокований Android Emulator; знайдено: ${#devices[@]}." >&2
    exit 1
fi

if [[ ${devices[0]} != emulator-* ]]; then
    echo "Цей тестовий скрипт призначений лише для Android Emulator (${devices[0]} не є емулятором)." >&2
    exit 1
fi

bash ./gradlew installDebug

if ! adb shell dpm set-device-owner "$ADMIN_RECEIVER"; then
    cat >&2 <<'EOF'
Не вдалося призначити Device Owner. Створіть новий AVD без Google Play,
не додавайте акаунти та виконайте Wipe Data перед повторним запуском скрипта.
EOF
    exit 1
fi

adb shell am start -n "$ACTIVITY"
echo "Kiosk запущено. Для виходу натисніть «Вийти» та введіть PIN 12345."
