# JavaBattleship

Описание проекта.

В проекте реализована игра "Морской бой". Поддерживается ввод из командной строки и из консоли.
Программа работает либо в одном, либо в другом режиме. Если в командную строку ничего не было передано,
то ввод данных осуществляется через консоль, в противном случае, ввод только из командной строки.
Если вы допустили ошибку во входных данных в командной строке, перезапустите программу с другими данными.

Так или иначе, программа ожидает на вход строку вида "m n x y z t s"
где m - число строк таблицы, n - число столбцов
x y z t s - количество керриеров, линкоров, крейсеров, разрушителей и субмарин соответственно.

m и n - оба > 0
x y z t s - все >= 0, однако все сразу быть нулевыми не могут.

Пример: 10 10 0 0 0 2 0

Далее компьютер генерирует поле и расставляет корабли. Алгоритм расстановки кораблей был реализован
максимально случайным образом, чтобы расположение не было предсказуемо. Поэтому иногда компьютеру
не удаётся расставить корабли с первого раза. Он напишет: Unfortunately, computer can not distribute the ships. Try again!
Чаще всего нужно только пару-тройку раз перегенирировать поле, для этого не выходите из программы, а сделайте retry.

Если же вы изначально задали поле, в котором все указанные корабли физически не поместятся, программа никогда не построит вам поле.
Не волнуйтесь, всё решается расширением размеров таблицы.

Если генерация удалась, то выведется следующее:
	Nice to meet you here:)
	Computer will draw the sea, your task is type coordinates for fire-attack on ships.
	Press enter to continue.
Каждый раз при обновлении таблицы будет выводиться подсказка с обозначениями:
Signs: ? - NOT FIRED    * - FIRED MISS    X - FIRED HIT    # - SUNK
Далее, выводится таблица из этих символов. ЦИфры слева - индексы строк, цифры справа - индексы столбцов.

Теперь нужно вводить координаты ячейки, которую хотите атаковать.
Вводите целочисленные индексы ячеек таблицы, сначала х, потом нажмите enter, потом введите y.

В случае промаха появится надпись "You missed :("
Если подбили корабль: "You hit the ship!
Если уничтожили последнюю ячейку корабля: You just have sunk a <тип корабля>

Ввод не прекратится, пока все корабли не будут уничтожены. Как только последний корабль будет затоплен,
На экране отобразятся ваши очки, набранные в процессе игры.


Do you want to retry or exit? (put "exit" to stop or type enter to retry)
Периодически программа будет предлагать вам выйти или перезапустить игру. Чтобы выйти, введите слово exit
и нажмите enter. 

Касательно функционала: Доп.функционал не реализован, всё остальное есть.
Если по какой-то причине программа не будет реагировать на одиночное нажатие enter, просто введите любую букву и нажмите enter.
