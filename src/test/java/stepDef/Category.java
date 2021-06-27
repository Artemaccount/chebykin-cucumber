package stepDef;

enum Category {
    АВТО("Автомобили", "9"),
    ОРГТЕХНИКА("Оргтехника и расходники", "99"),
    ТЕЛЕФОНЫ("Телефоны", "84"),

    УМОЛЧ("По умолчанию", "101"),
    ДЕШЕВЛЕ("Дешевле", "1"),
    ДОРОЖЕ("Дороже", "2"),
    ДАТА("По дате", "104");


    String name;
    String number;

    Category(String name, String number) {
        this.name = name;
        this.number = number;
    }


    public String getNumber() {
        return number;
    }
}
