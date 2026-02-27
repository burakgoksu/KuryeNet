# Messaging (RabbitMQ)

Messaging is optional and disabled by default.

## Enable
Set:
- `APP_MESSAGING_ENABLED=true`

## Configuration
```
app.messaging.enabled=false
app.messaging.order-exchange=order.exchange
app.messaging.order-queue=order.events
app.messaging.order-routing-key=order.events
```

## Behavior
- When enabled, `OrderManager.add` publishes an `ORDER_CREATED` event.
- `OrderEventListener` consumes events and logs them.
