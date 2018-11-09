# gais-gw
Зависимости:

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
