Feature: Проверка корректности ответа

  Scenario: Проверка корректного ответа в БД
    Given был передан id паттерна '3'
    When дан ответ "цепочка обязанностей"
    Then вернуть флаг "true"

  Scenario: Проверка некорректного ответа в БД
    Given был передан id паттерна '3'
    When дан ответ "адаптер"
    Then вернуть флаг "false"
