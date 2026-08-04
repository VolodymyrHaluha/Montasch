# Montasch — тест Kiosk Mode в Android Emulator

Застосунок використовує **Device Owner**, **Lock Task Mode** і реєструється як
постійний HOME-застосунок. Це блокує Home, Recents, шторку сповіщень і вихід із
застосунку. Кнопка **«Вийти»** з PIN `12345` зупиняє Lock Task та відкриває
налаштування Android.

## Підготовка емулятора

1. В Android Studio відкрийте **Device Manager → Add a new device**.
2. Створіть AVD з образом Android без позначки **Google Play**. Device Owner не
   можна призначити на вже налаштований емулятор з акаунтами.
3. Запустіть AVD. Якщо він використовувався раніше, виберіть **Wipe Data**, а
   після запуску не додавайте Google-акаунт і не створюйте робочий профіль.
4. Переконайтеся, що `adb devices` показує рівно один пристрій зі статусом
   `device`.

Фізичний Android-пристрій для цього тесту не потрібен.

## Автоматичне встановлення (macOS/Linux)

З кореня проєкту виконайте:

```bash
chmod +x setup-kiosk-emulator.sh
./setup-kiosk-emulator.sh
```

Скрипт перевіряє, що підключено саме емулятор, збирає та встановлює debug APK,
призначає застосунок Device Owner і запускає Kiosk.

## Ручне встановлення (Windows PowerShell)

```powershell
.\gradlew.bat installDebug
adb shell dpm set-device-owner com.example.montasch/.KioskDeviceAdminReceiver
adb shell am start -n com.example.montasch/.MainActivity
```

Перевірити системну роль можна командою:

```powershell
adb shell dpm list owners
```

У відповіді `com.example.montasch` має бути позначений як Device Owner. Якщо
`dpm set-device-owner` повідомляє, що пристрій уже provisioned, виконайте **Wipe
Data** для AVD та повторіть інсталяцію на чистому емуляторі.

## Перевірка

- Home, Recents і системна шторка недоступні.
- Після `adb reboot` Montasch запускається автоматично та знову входить у Lock
  Task Mode.
- Натискання системної кнопки Back не закриває застосунок.
- **«Вийти» → PIN `12345`** повертає доступ до налаштувань Android.

Для повторного чистого тесту скористайтеся **Device Manager → Wipe Data**.
Звичайного встановлення APK недостатньо: системну роль Device Owner надає лише
`adb` на чистому тестовому AVD (або Android Enterprise provisioning у production).
