package ru.geekstar.Card;

public interface ICashbackCard {

    // накапливаем кэшбэк
    void accumulateCashback(float sumPay);

    // зачисляем кэшбэк на карту
    void depositingCashback2Card();

}
