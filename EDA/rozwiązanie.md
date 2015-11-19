Do zadania wykorzystałem BookCorpus.

#Zadanie a

#####Konwersja na plik csv została wykonan za pomocą programu BookToCSV zaimplementowanym w jezyku java. [Link do kodu](https://github.com/rjasinski/nosql/blob/master/EDA/BookToCSV.java)

Program wykorzystuje zbiór standardowych słów stopu dla języka angielskiego zapisanych w pliku [stopword.txt](https://github.com/rjasinski/nosql/blob/master/EDA/stopword.txt). Dodatkowo po wstępnym zajrzeniu do danych okaząło się że znaki interpunkcyjne bedą wykrywane jako słowa. Z tego powodu zostały za wczasu dodane do słów stopu.

#####Polecenie konwersji

java BookToCSV ~/Downloads/books_large_p1.txt book_large_p1.csv

######Czas konwersji:

    real: 4m23.113s
    user: 3m20.986s
    sys: 0m23.368s

Powstały plik zaś ma rozmiar 5.7GB

![Wykres pamięci](https://github.com/rjasinski/nosql/blob/master/EDA/grafika/konwersja.png)

Jak widać proste operacje plikowe nie stanowia problemu dla dzisiejszych komputerów pomimo dużych danych.

####PostgreSQL
#####Utworzenie tabeli poleceniem:
psql -U rjasisnki -d rjasisnki -c "create table book (ID numeric, word text, line numeric);"
#####Wczytanie danych poleceniem:
psql -U rjasisnki -d rjasisnki -c "copy book(ID, word, line) from '/home/rjasisnki/EDA/book_large_p1.csv' with delimiter ',' csv header"

######Czas wgrywania danych do bazy:

    real: 7m20.421s
    user: 0m0.022s
    sys: 0m0.035s

![Wykres pamięci](https://github.com/rjasinski/nosql/blob/master/EDA/grafika/import_psql.png)

Wynik ten jest dość zaskakujacy, dane zostały wczytane niezwykle szybko, a w dodatku obciążenie procesora było ardzo niskie. Tym razme jednak wykorzystane były aż dwa rdzenie co sugerowało by współbieżność tego procesu.

####MongoDB
#####Wczytanie do bazy poleceniem:
mongoimport -d fnsql -c train --type csv --file ~/EDA/book_large_p1.csv --headerline

######Czas wgrywania danych do bazy:

    real: 68m37.284s
    user: 11m19.003s
    sys: 1m57.698s
![Wykres pamięci](https://github.com/rjasinski/nosql/blob/master/EDA/grafika/wczytywanie%20mongo.png)

Czas wczytywania był długi. Po pracy procesora wyraźnie widać wielowątkowość. Jednak na niepełne obciążenie go miał dysk który nie nadążał.


#Zadanie b
##Zliczenie rekordów w bazie
###PostgreSQL
#####Polecenie:
psql -U rjasisnki -d rjasisnki -c "select count(*) from book"

Wynik: 245840578

######Czas zliczania:

    real: 3m10.781s
    user: 0m0.070s
    sys: 0m0.028s

![Wykres pamięci](https://github.com/rjasinski/nosql/blob/master/EDA/grafika/zliczanie_psql.png)

Jak widać zliczanie nie jest operacją obciążajacą komputer.

####MongoDB
#####Polecenia w konsoli mongo:
db.train.count()

Wynik: 245848691

Czas był prawie zerowy.

#Zadanie c
##Zliczenie unikalnych słów
###PostgreSQL
#####Polecenie:
psql -U rjasisnki -d rjasisnki -c "select count(distinct word) from book"

Wynik: 1051914

######Czas zliczania:

    real: 67m28.964s
    user: 0m0.022ss
    sys: 0m0.061s

![Wykres pamięci](https://github.com/rjasinski/nosql/blob/master/EDA/grafika/psql_zliczanie_uniklanych.png)
Z tą operacją PostgreSQL juz dużo gorzej sobie radził. Ciekawy jest jednak fakt ze obciążony był jeden rdzeń procesora w sposób stały i nie dochodziło do przetasowań.

####MongoDB
#####Polecenia w konsoli mongo:
db.train.distinct("word").length

Wynik:

distinct failed: {
	"errmsg" : "exception: distinct too big, 16mb cap"

Po wyszukaniu i zapoznaniu się z informacjami o tym błędzie wynika że do agregacji tak dużych zbiorów konieczne jest użycie techniki map reduce.

##Wnioski
| PostgreSQL                  | MongoDB           | 
|-----------------------------|-------------------|
| + szybki import           | + baza gotowa do działanai po instalacji    |
| + poradzenie sobie z dużym zbiorem danych | + intuicyjne polecenie pisane w ciągu sekund |
| - długi czas najprostrzych agregacji | + szybkie zliczenie |
| - ogromny czas poświęcony na konfiguracje | - długi import          |
| - żmudne i niewygodne tworzenei poleceń które zadziałąją | - nieradzenie sobie z agregacjami na tak dużym zbiorze |
