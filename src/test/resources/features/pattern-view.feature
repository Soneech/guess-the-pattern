Feature: GET-запросы на получение страницы с ссылками на задания и страницы конкретного паттерна

  Scenario: GET-запрос на patterns
    When клиент переходит на "patterns"
    Then вернуть страницу "patterns.html" с ссылками на задания

  Scenario: GET-запрос на /patterns/{id}
    Given был передан id паттерна '3'
    When клиент переходит на patterns '3'
    Then вернуть страницу "pattern_page" с данными паттерна, найденного по id '3'