Do zadania wykorzystałem BookCorpus.

#Zadanie 2a

#####Konwersja na plik csv została wykonan za pomocą programu BookToCSV zaimplementowanym w jezyku java. [Link do kodu](https://github.com/rjasinski/nosql/blob/master/EDA/BookToCSV.java)

#####Polecenie konwersji

java BookToCSV ~/Downloads/books_large_p1.txt book_large_p1.csv

######Czas konwersji:

    real: 4m23.113s
    user: 3m20.986s
    sys: 0m23.368s
Powstały plik zaś ma rozmiar 5.7GB

####MongoDB
#####Wczytanie do bazy poleceniem:
mongoimport -d fnsql -c train --type csv --file ~/EDA/book_large_p1.csv --headerline

######Czas wgrywania danych do bazy:

    real: 68m37.284s
    user: 11m19.003s
    sys: 1m57.698s
