# StockTradingClientBackEnd



# We all prefer Python to Java

# API

## `/api/record`

- Request  **POST only**

  ```JSON
  {
      userinfo: '111'
  }
  ```

- Response

```Json

[
        {
          key: '1',
          sellOrBuy: 'Sell',
          stockId: 'A007',
          number: 32,
          time: 'xxxxxx',
          states: ['交易中', '交易完成'],
        },
        {
          key: '2',
          sellOrBuy: 'Sell',
          stockId: 'A008 拼多多',
          number: 64,
          time: 'xxxxxx',
          states: ['交易中', '交易完成'],
        }
      ];
```

## `api/mystock`

- 666



# License

- STFW

#### 股票查询后端

https://github.com/Jiaofuf/StockClientQueryBackEnd
