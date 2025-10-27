Se desea hacer un programa Java que permita jugar a “Medio Tateti”. Es un juego para dos personas, donde se
alternan turnos. Un jugador ubica piezas de color Blanco y su objetivo es formar 3 letras “O” alineadas (horizontal,
vertical o diagonal, sin importar el color de las piezas) y el otro jugador ubica piezas de color Negro y su objetivo
es formar 3 letras “X” alineadas (sin importar el color). Comienza el blanco.

En su turno, el jugador:
- puede colocar en un lugar libre una pieza de su color indicando la fila, columna y orientación (ej. “A3C”,
“B2D”) ó
- puede invertir una pieza propia (ej. “A2I”, “B3I”). Invertir implica simetrizar la pieza: si era “C” pasa a
“D” y viceversa.
En cualquier caso, si la jugada es inválida, la reingresa. No se puede pasar el turno.
Si se indica como jugada “X” termina el juego y pierde.
Si indica “B”, se muestran los títulos de las columnas y filas y si se indica “N”, se ocultan.
Si se indica “T”, se solicita confirmación y de ser confirmado, se asume que se desea terminar de mutuo acuerdo
y se considera empate.
Si se detecta ganador, termina.
Si indica “H”, el sistema brinda información de si hay una jugada ganadora, es decir, si ese jugador realiza la
jugada que indica el sistema, gana el jugador. De haber varias, indicar cualquiera de ellas. Si no hay, indicarlo.
Luego de realizar su jugada, se debe verificar si hay ganador. Para ganar debe haber 3 letras “O” o 3 letras “X”
alineadas, SIN IMPORTAR el color de los elementos que la integran, gana el jugador de la letra
correspondiente.

Se pide implementar en Java un programa que debe ofrecer un menú en consola con este título:
Trabajo desarrollado por: NOMBRE y NUMERO DE LOS AUTORES
y estas opciones:
1) Registrar un jugador: se indica nombre (único, no se realiza ninguna otra validación) y edad.
2) Comienzo de partida común: se muestra la lista de jugadores ordenada alfabéticamente y numerada y se
eligen por su número dos jugadores diferentes. Siempre se indica en pantalla de quién es el turno (jugador
Blanco o Negro). La jugada se indica: FilaColumnaSentido siendo: Fila: “A”, “B” o “C”. Columna 1 a 6.
Sentido “C”, “D” o “I”. Si la jugada es “X”, termina y pierde. Si la jugada es “H”, brinda ayuda. Si la
jugada es “B”, al mostrar el tablero incluye los títulos de los bordes. Si la jugada es “N”, al mostrar el
tablero no incluye los títulos de los bordes. Si la jugada es “T”, previa confirmación termina en empate.
Luego de cada jugada, mostrar el tablero. Si la jugada es incorrecta, se reingresa. No se puede pasar el
turno.
El programa controla el fin de la partida. Al terminar una partida, informa el resultado.
3) Continuación de partida. Se eligen los jugadores (en forma similar a la partida común) y se ingresa un
string que contiene una lista de movimientos (ej: “A1C B3B C1D”). Debe aplicarse esa secuencia (se
asume válida) y continúa desde ahí la partida como una partida común. Se debe mostrar el tablero luego
de cada una de las jugadas de esa secuencia.
4) Mostrar ranking e invictos. Debe mostrarse el ranking de los jugadores ordenados decreciente por
cantidad de partidas ganadas. En otra lista mostrar los jugadores invictos, que nunca jugaron y, o, nunca
perdieron, ordenados alfabéticamente.
5) Terminar el programa.
