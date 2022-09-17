package ru.geekstar.Bank;

import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;

public class Bank {

    private String bankName;

    private SberPhysicalPersonProfile[] clientProfiles = new SberPhysicalPersonProfile[5];

    private int countClientProfiles;


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public SberPhysicalPersonProfile[] getClientProfiles() {
        return clientProfiles;
    }

    public void setClientProfiles(SberPhysicalPersonProfile[] clientProfiles) {
        this.clientProfiles = clientProfiles;
    }

    public int getCountClientProfiles() {
        return countClientProfiles;
    }

    public void setCountClientProfiles(int countClientProfiles) {
        this.countClientProfiles = countClientProfiles;
    }

    // Добавить профиль клиента
    public void addClientProfile(SberPhysicalPersonProfile clientProfile) {
        clientProfiles[countClientProfiles++] = clientProfile;
    }

    // Сгенерировать номер карты 3546 0957 9843 7845
    public String generateNumberCard() {
        byte lengthNumberCard = 20;
        StringBuffer numberCardBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberCard; i++) {   // i = 1, чтобы не было пробела в начале, потому что при делении 0 на 5 будет 0
            if (i % 5 != 0) numberCardBuffer.append((byte) Math.random() * 10);
            else numberCardBuffer.append(' ');
        }
        return numberCardBuffer.toString();
    }

    // Сгенерировать номер счёта 35467095799843178451
    public String generateNumberAccount() {
        byte lengthNumberAccount = 20;
        StringBuffer numberAccountBuffer = new StringBuffer();
        for (byte i = 0; i < lengthNumberAccount; i++) {
            numberAccountBuffer.append((byte) Math.random() * 10);
        }
        return numberAccountBuffer.toString();
    }

}
