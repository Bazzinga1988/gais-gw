# gais-gw
Зависимости:
/**
* Тип данных
*/
private String type;

/**
* Источник данных
*/
private int source;

/**
* Потребитель данных
*/
private int destination;

/**
* Тип действия
*/
private int action;

/**
* Время отправки
*/
private LocalDateTime utc;

/**
* Пользователь
*/
private String user;

/**
* Данные в формате JSON
*/
private String payload;



gc-java-common: 0.7.4-SNAPSHOT

gc-stream-common: 0.1.3-SNAPSHOT

Данные - DataWrapper:

Тип данных / type - ?

Источник данных / source - DataSources.GAIS_GATEWAY

Потребитель данных / destination - ?

Тип действия / action - ActionTypes.CREATE

Время отправки / utc - текущее время в UTC

Пользователь / user - 'gais gateway'

Данные в формате JSON / payload - компания с вложенными в нее авто и устройствами

Для устройств -

ID оператора - Operators.GLONASS

ID устройства - должен вернуть Глонасс

