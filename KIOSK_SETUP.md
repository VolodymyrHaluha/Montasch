# Налаштування справжнього Kiosk Mode

Android дозволяє застосунку самостійно ввімкнути повний Lock Task Mode лише тоді,
коли застосунок призначений **Device Owner**. Без цього Android запускає звичайне
закріплення екрана, з якого користувач може вийти системною комбінацією кнопок.

## Debug-збірка на чистому емуляторі

1. Видаліть усі Google-акаунти з емулятора або створіть новий емулятор без акаунтів.
2. Встановіть debug-збірку:

   ```powershell
   .\gradlew.bat installDebug
   ```

3. Призначте застосунок Device Owner **до першого звичайного налаштування пристрою**:

   ```powershell
   adb shell dpm set-device-owner com.example.montasch.debug/com.example.montasch.KioskDeviceAdminReceiver
   ```

4. Запустіть застосунок:

   ```powershell
   adb shell am start -n com.example.montasch.debug/com.example.montasch.MainActivity
   ```

Після запуску застосунок автоматично приховує системні панелі та входить у Lock
Task Mode. Системна кнопка «Назад» вимкнена. Штатний вихід виконується лише через
кнопку **«Вийти»** та підтвердження в діалозі.

Коли Device Owner налаштовано правильно, Android **не показує** системне вікно
«Ця програма закріплена». Це вікно належить звичайному Screen Pinning і означає,
що застосунок не був призначений Device Owner. Код навмисно не викликає
`startLockTask()` без Device Owner allowlist, щоб таке повідомлення не зʼявлялося.

## Автозапуск після перезавантаження

Device Owner реєструє Montasch як постійний HOME-застосунок. Додатково receiver
обробляє `LOCKED_BOOT_COMPLETED` і `BOOT_COMPLETED`. Тому після завантаження
пристрою `MainActivity` запускається автоматично, повертає immersive mode та
повторно входить у Lock Task Mode. Перевірити це можна командою:

```powershell
adb reboot
```

## Release-збірка

Для release-пакета використовуйте компонент без суфікса `.debug`:

```powershell
adb shell dpm set-device-owner com.example.montasch/com.example.montasch.KioskDeviceAdminReceiver
```

Команда `dpm set-device-owner` працює лише на чистому пристрої без акаунтів та
іншого Device Owner. Для промислового розгортання Device Owner слід призначати
через QR provisioning або вашу EMM-систему.
