Feature: GET-запросы на получение страницы с ссылками на задания и страницы конкретного паттерна

  Scenario: GET-запрос на patterns
    When клиент переходит на "patterns"
    Then вернуть страницу "patterns.html" с ссылками на задания