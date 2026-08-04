# Montasch — справжній Android Kiosk Mode

Цей проєкт використовує **Device Owner + Lock Task Mode + persistent HOME app**.
Без призначення Device Owner Android завжди запускатиме Montasch як звичайний
застосунок, і користувач зможе натиснути Home або відкрити Recents.

## 1. Підготуйте телефон

Для тестового встановлення найнадійніший варіант — скинути телефон до заводських
налаштувань. Під час початкового налаштування не додавайте Google-акаунт, робочий
профіль або інших користувачів. Увімкніть Developer options та USB debugging.

## 2. Встановіть застосунок

У PowerShell з кореня проєкту:

```powershell
.\gradlew.bat installDebug
```

У виправленій збірці debug і release використовують один package name:
`com.example.montasch`.

## 3. Призначте Montasch Device Owner

```powershell
adb shell dpm set-device-owner com.example.montasch/com.example.montasch.KioskDeviceAdminReceiver
```

Перевірка:

```powershell
adb shell dpm list owners
```

У результаті `com.example.montasch` має бути позначений як Device Owner.
Якщо команда повідомляє, що пристрій уже provisioned, має акаунти або іншого
власника, виконайте factory reset та повторіть без додавання акаунтів.

## 4. Запустіть Montasch

```powershell
adb shell am start -n com.example.montasch/com.example.montasch.MainActivity
```

Після першого запуску застосунок:

- додає себе до Lock Task allowlist;
- вимикає Home, Recents, шторку, повідомлення та power menu в Lock Task;
- стає постійним HOME-застосунком;
- запускається після перезавантаження;
- тримає екран увімкненим під час роботи;
- дозволяє вийти лише кнопкою «Вийти» з PIN `12345`.

## 5. Перевірте перезапуск

```powershell
adb reboot
```

Після завантаження Android повинен автоматично відкрити Montasch та повернути
Lock Task Mode.

## Важливо

Просто встановити APK недостатньо. Device Owner — це системна роль, яку не можна
надійно отримати натисканням кнопки всередині звичайного застосунку. Для серійного
розгортання використовуйте Android Enterprise QR provisioning або EMM.

Коротке натискання фізичної кнопки живлення може погасити дисплей на деяких
моделях. Після повторного ввімкнення екрана користувач має повернутися до Montasch;
повністю перепризначити фізичну кнопку живлення звичайний Android SDK не дозволяє.
