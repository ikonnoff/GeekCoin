### Приложение для управления личным бюджетом с элементами реального банкинга ###

**⭐️ Реализовано:**<br/>
1. Графический интерфейс пользователя<br/>
2. Регистрация пользователя<br/>
3. Открытие банковских карт платёжных систем МИР, Visa и Mastercard — обычных карт, мультивалютных (₽, $ и €), с кэшбэком, накоплением бонусов и миль)<br/>
4. Открытие счетов (платёжных и сберегательных) в разных валютах — ₽, $ и €<br/>
5. Добавление мультисчетов к мультивалютной карте<br/>
6. Переключение между мультисчетами мультивалютной карты<br/>
7. Проведение различных финансовых операций — оплат (в том числе за рубежом), переводов (с карты на карту, с карты на счёт, со счёта на карту, со счёта на счёт) и пополнений<br/>
8. Проведение авторизации и блокирование суммы списания при оплате картой или переводе с карты на карту<br/>
9. Оплата с вводом пин-кода<br/>
10. Оплата до 99% бонусами и до 100% милями с оплатой остатка картой<br/>
11. Оплаты за рубежом в разных странах — Казахстане, Турции и Франции с конвертацией по курсу платёжной системы и банка<br/>
12. Переводы в разных валютах с конвертацией по курсу банка<br/>
13. Отображение транзакций по всем операциям<br/>
14. Определение лимитов при оплате или переводе<br/>
15. Расчёт комиссий при оплате или переводе<br/>
16. Определение от кого перевод при переводе на чужую карту или на карту другого банка<br/>
17. Различные проверки и защиты — проверка заполнения полей ввода, введённых сумм, недоступности оплаты картой МИР во Франции и др.<br/>

**Требования к счетам и картам, открытым в любом банке:**<br/>
✅ Номер карты состоит из 16 символов и только цифр<br/>
🖥 Генерация номера карты реализована в методе: generateNumberCard() класса Bank<br/>
🖥 Проверка реализована в методе: setNumberCard() класса Card

✅ Платёжный счёт открывается только при открытии карты и привязывается к ней<br/>
🖥 Открытие платёжного счёта реализовано в перегруженном методе: openAccount() класса Bank<br/>
🖥 Открытие карты и привязка к ней платёжного счёта реализованы в методе: openCard() класса Sberbank

✅ На балансе счёта не может быть отрицательных значений<br/>
🖥 Проверка реализована в методе: setBalance() класса Account

✅ Допустимы счета только в трёх валютах: ₽, $ и €<br/>
🖥 Проверка реализована в методе: setCurrencySymbol() класса Account

✅ Заложить на будущее начисление процентов на остаток по сберегательному счёту в конце месяца<br/>
🖥 Реализовано в методе: chargePercentOnBalanceEndMonth() класса SavingsAccount


**Требования к профилю клиента Сбербанка:**<br/>
✅ Объект профиля клиента создаётся банком со всеми нижеуказанными значениями процентов и лимитов по умолчанию<br/>
🖥 Реализовано в методе: registerClientProfile() класса Sberbank

✅ Лимит на сумму всех оплат и переводов клиентам Сбера и других банков в сутки в рублях - 1000000 ₽<br/>
✅ Лимит на сумму всех оплат и переводов клиентам Сбера и других банков в сутки в долларах - 50000 $<br/>
✅ Лимит на сумму всех оплат и переводов клиентам Сбера и других банков в сутки в евро - 3800 €<br/>
🖥 Расчёт и обновление общей суммы совершенных оплат и переводов в сутки реализовано в перегруженных методах: updateTotalPaymentsTransfersDay() в классах ClientProfile и PhysicalPersonProfile<br/>
🖥 Проверка на превышение лимита реализована в методе: exceededLimitPaymentsTransfersDay() класса ClientProfile

✅ Лимит на сумму всех переводов клиентам Сбера без комиссии в месяц в рублях - 50000 ₽<br/>
🖥 Расчёт и обновление общей суммы переводов клиентам Сбера без комиссии в месяц реализовано в перегруженных методах: updateTotalTransfersToClientSberWithoutCommissionMonthInRUB() в классе SberPhysicalPersonProfile<br/>
🖥 Проверка на превышение лимита реализована в методе: exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB() класса SberPhysicalPersonProfile

✅ Процент комиссии от суммы одного перевода в рублях:<br/>
🟢 -на свою карту или свой счёт в Сбере - 0%<br/>
🖥 Расчёт реализован в перегруженных методах: getCommission() класса Bank<br/>
🖥 Определение принадлежности карты или счета физ. лицу реализовано в методах: isClientCard() и isClientAccount() в классе PhysocalPersonProfile<br/>
🖥 Определение принадлежности карты или счета банку реализовано в методах: isCardBank() и isAccountBank() в классе Bank<br/>
🟢 -клиентам Сбера, если лимит всех переводов в месяц превышен - 1%<br/>
🖥 Расчёт реализован в переопределённом методе: getCommissionOfTransferToClientBank() класса Sberbank<br/>
🟢 -клиентам других банков - 1%<br/>
🖥 Расчёт реализован в методе: getCommissionOfTransferToClientAnotherBank() класса Bank

✅ Процент комиссии от суммы одного перевода в долларах или другой валюте:<br/>
🟢 -клиентам Сбера - 0%<br/>
🖥 Расчёт реализован в переопределённом методе: getCommissionOfTransferToClientBank() класса Sberbank<br/>
🟢 -клиентам других банков - 1.25%<br/>
🖥 Расчёт реализован в методе: getCommissionOfTransferToClientAnotherBank() класса Bank

✅ Процент комиссии за оплату услуг «ЖКХ» - 2%<br/>
🖥 Расчёт реализован в перегруженном методе, принимающий сумму оплаты и название продукта: getCommission() класса Bank

✅ Лимит на сумму комиссии за один перевод в рублях - 3000 ₽<br/>
✅ Лимит на сумму комиссии за один перевод в долларах или эквивалент в другой валюте - 100 $<br/>
🖥 Проверка реализована в методе: exceededLimitCommission() класса Bank


**Требования к операциям:**<br/>
✅ При оплате внутри своей страны конвертация через валюту платёжной системы не производится, так как операции обрабатываются Национальной системой платежных карт<br/>
🖥 Соблюдено в перегруженном методе оплаты внутри своей страны: payByCard() класса Card

✅ При оплате картой Сбер Visa Gold заложить на будущее начисление сбербонусов.<br/>
🖥 Реализовано в переопределённом методе: payByCard() класса SberVisaGold

✅ При оплате картой проводить авторизацию:<br/>
🖥 Алгоритм авторизации реализован в методе: authorization() класса Bank<br/>
🟢 -сгенерировать 6-ти значный код авторизации<br/>
🖥 Генерация кода авторизации реализована в методе: generateAuthorizationCode() класса Bank<br/>
🟢 -проверить статус карты: активна или заблокирована<br/>
🟢 -проверить баланс и достаточно ли денег<br/>
🟢 -проверить не превышен ли лимит по оплатам и переводам в сутки в ₽, $ и €<br/>
🟢 -заблокировать сумму на счёте карты, чтобы нельзя было её использовать в других операциях проводимых в этот же момент времени<br/>
🖥 Блокирование суммы на счёте карты реализовано в методе: blockSum() класса PayCardAccount<br/>
🖥 Списание заблокированной суммы реализовано в методе: writeOffBlockedSum() класса PayCardAccount

✅ При оплате за рубежом, если валюта покупки и валюта счёта карты не совпадают, то необходимо:<br/>
-выполнить конвертацию сумму покупки в валюту биллинга (платёжной системы) по курсу платёжной системы;<br/>
-затем полученную сумму в валюте биллинга конвертировать в валюту карты по курсу банка и выполнить списание с карты.<br/>
🟢 -валюта, в которой выполняет операции платёжная система Visa: $<br/>
🟢 -валюта, в которой выполняет операции платёжная система Mastercard: $ и € (при оплате в Еврозоне)<br/>
🖥 Определение обменного курса платёжной системы реализовано в методах: getExchangeRatePaySystem() класса CardVisa и CardMastercard<br/>
🖥 Определение обменного курса банка реализован в переопределённом методе: getExchangeRateBank() класса Sberbank<br/>
🖥 Конвертация реализована в переопределённых методах: convertToCurrencyExchangeRatePaySystem() в классах CardVisa и CardMastercard<br/>
🖥 Вызов методов конвертации происходит в перегруженном методе оплаты, принимающий название страны, в которой совершается оплата: payByCard() класса Card

✅ При переводе с карты на карту проводить авторизацию (аналогичную как и при оплате картой) с блокированием суммы на счёте карты списания<br/>
🖥 Вызов метода авторизации еализован в методе перевода с карты на карту: transferCard2Card() класса Card

✅ При переводе с карты на счёт, со счёта на карту и со счёта на счёт проводить авторизацию не нужно, а только проверку баланса и достаточно ли денег для перевода<br/>
🖥 Проверка баланса и достаточно ли денег реализована в методе: checkBalance() класса Account

✅ При внесении наличных на карту произвести авторизацию только в части проверки статуса карты: активна или заблокирована карта<br/>
🖥 Авторизация только в части проверки статуса карты реализована в методе: authorization() класса Bank


**Требования к отображению операций:**<br/>
✅ Вывод операций по счету<br/>
🖥 Реализовано в методе: displayAccountTransactions() в классе Account

✅ Вывод операций по карте<br/>
🖥 Реализовано в переопределённом методе: displayAccountTransactions() в классе PayCardAccount

✅ Вывод операций по всем картам и счетам профиля клиента с сортировкой по дате и времени<br/>
🖥 Реализовано в переопределённых методах: displayProfileTransactions() в классах PhysicalPersonProfile и SberPhysicalPersonProfile
