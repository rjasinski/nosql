Do zadania wykorzystałem BookCorpus.

#Zadanie a

#####Konwersja na plik csv została wykonan za pomocą programu BookToCSV zaimplementowanym w jezyku java. [Link do kodu](https://github.com/rjasinski/nosql/blob/master/EDA/BookToCSV.java)

#####Polecenie konwersji

java BookToCSV ~/Downloads/books_large_p1.txt book_large_p1.csv

######Czas konwersji:

    real: 4m23.113s
    user: 3m20.986s
    sys: 0m23.368s
Powstały plik zaś ma rozmiar 5.7GB

####PostgreSQL
#####Utworzenie tabeli poleceniem:
psql -U rjasisnki -d rjasisnki -c "create table book (ID numeric, word text, line numeric);"
#####Wczytanie danych poleceniem:
psql -U rjasisnki -d rjasisnki -c "copy book(ID, word, line) from '/home/rjasisnki/EDA/book_large_p1.csv' with delimiter ',' csv header"

######Czas wgrywania danych do bazy:

    real: 7m20.421s
    user: 0m0.022s
    sys: 0m0.035s

####MongoDB
#####Wczytanie do bazy poleceniem:
mongoimport -d fnsql -c train --type csv --file ~/EDA/book_large_p1.csv --headerline

######Czas wgrywania danych do bazy:

    real: 68m37.284s
    user: 11m19.003s
    sys: 1m57.698s

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

####MongoDB
#####Polecenia w konsoli mongo:
db.train.distinct("word").length

Wynik:

distinct failed: {
	"errmsg" : "exception: distinct too big, 16mb cap"


