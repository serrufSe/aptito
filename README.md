# aptito

# REST API

## POST order
Создание заказа
### Пример запроса
```
curl -X POST http://localhost:8080/order -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"name": "test", "amount": 666}'
 ```
  
### Формат входных данных
| Название поля | Тип | Описание |
|---|---|---|
| name | str | Имя заказа |
| amount | int | сумма денег за заказ |


### Пример тела ответа
```json
{
    "id": 12,
    "name": "test",
    "amount": 666,
    "complete": false
}
```

## PATCH order/{id}
Обновление заказа
### Пример запроса
```
curl -X PATCH http://localhost:8080/order/2 -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"name": "kwest", "amount": 666}' 
  ```
  
### Формат входных данных
| Название поля | Тип | Описание |
|---|---|---|
| name | str | Имя заказа |
| amount | int | сумма денег за заказ |


#### Коды ответа:

Код | Описание
--- | --------
200 | Ок
400 | Переплата
иные | Что-то пошло не так


## GET orders
Список заказов
### Пример запроса
```
curl -X GET 'http://localhost:8080/orders?name=kwest' -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{"name": "kwest", "amount": 666}' 
```

Код | Описание
--- | --------
200 | Ок
400 | Переплата
иные | Что-то пошло не так
  
### Формат входных данных
| Название поля | Тип | Описание |
|---|---|---|
| name | str | Имя заказа |
| completed | bool | заказ оплачен |


### Пример тела ответа
```json
[
    {
        "id": 2,
        "name": "kwest",
        "amount": 666,
        "complete": false
    }
]
```



## GET transactions
Список транзакций
### Пример запроса
```
curl -X GET http://localhost:8080/transactions -H 'cache-control: no-cache' -H 'content-type: application/json'
```
 


### Пример тела ответа
```json
[
    {
        "id": 1,
        "amount": 100,
        "order": {
            "id": 2,
            "name": "kwest",
            "amount": 666,
            "complete": false
        }
    }
]
```


## GET order/{orderId}/rest
Остаток по заказу
### Пример запроса
```
curl -X GET http://localhost:8080/order/2/rest -H 'cache-control: no-cache' -H 'content-type: application/json'
  ```
 

### Пример тела ответа
```json
{"rest": 500}
```


## GET statistics
Получение суммы транзакций за период времени
### Пример запроса
```
curl -X GET 'http://localhost:8080/statistics?startDate=2018-01-22&endDate=2018-01-25H12%3A00' -H 'cache-control: no-cache' -H 'content-type: application/json' 
 ```

### Пример тела ответа
```json
{"sum": 500}
```

### Формат входных данных
| Название поля | Тип | Описание |
|---|---|---|
| startDate | date format yyyy-MM-dd | Левая граница |
| endDate | date format yyyy-MM-dd | Правая граница |


## POST order/{orderId}/pay
Оплата заказа
### Пример запроса
```
curl -X POST \
  http://localhost:8080/order/13/pay \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"amount": 11}' 
 ```

### Формат входных данных
| Название поля | Тип | Описание |
|---|---|---|
| amount | int | Сумма транзакции |
