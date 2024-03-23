#Audies Party 

### Write a brief explanation of why you have chosen the greedy algorithm to solve the problem.
Elegí un algoritmo greedy para resolver este problema por que es un enfoque directo y eficiente para seleccionar a los invitados a la fiesta de Audie y dividirlos en grupos. 
Los invitados se representan como nodos en un gráfico, donde las relaciones amistosas entre ellos se reflejan en los pesos que conectan estos nodos cada peso representa el valor de la relación amistosa entre dos invitados y apartir de eso siendo este un problema de optimizacion pues subdividiamos a los invitados dependiendo su peso y asi podiamos encontrar sus grupos la relacion mas fuerte y la mas debil

### Identify the time complexity of your solution.
####Clases Edge y Node:
Métodos principalmente de acceso y comparación. Complejidad: O(1)
####Clases Directed y Undirected:
removeNode: O(S + K)
addEdge: O(1)
removeEdge: O(K)
updateEdge: O(K)
getAllEdges, getAllNodes, etc.: O(S + K)
####Clase Graph:
Métodos que manipulan conjuntos y mapas: O(1) o O(log N)
bfs: O(S + K)
getNumberConnectedNodes: O(S + K)
kruskal: O(K log S)

####Clase MaxSubGraph:
getMaxGraph: O(S * (S + K))

####Clase Kruskal:
fillSubset: O(S)
clean y convertToUndirectGraph: O(K)
kruskal: O(K log S)

####Clase FileGraphParser:
parseGraph: Lineal con respecto al número de líneas en el archivo

####Clase Operations:
removeEdgesLessThan y removeEmptyNodes: O(S + K)
findGroups: O(S * (S + K))

La complejidad del proyecto depende del tamaño del grafo de entrada (S: número de nodos, K: número de bordes) la complejidad total sería  la complejidad dominante,que es O(K log S) debido al algoritmo de Kruskal.

###Evidencias de los Resultados 
####Ejemplo 1:
![imagen](https://github.com/SebasthianSalpozz/Algoritmia2/assets/90279053/471c75b7-b7f5-4db9-8924-4217533b2484)
####Ejemplo 2 
#####Caso 1
![Captura desde 2024-03-23 14-09-02](https://github.com/SebasthianSalpozz/Algoritmia2/assets/90279053/e4ea3cc0-d51c-4be6-9b10-7d17605c72ea)
#####Caso 2
![imagen](https://github.com/SebasthianSalpozz/Algoritmia2/assets/90279053/9165ead0-11e9-40ae-b068-3c76541ddf3c)

#####Caso 3
![imagen](https://github.com/SebasthianSalpozz/Algoritmia2/assets/90279053/f19956e3-e387-4ba1-87dd-d7018659b6fa)

####Ejemplo 3
![imagen](https://github.com/SebasthianSalpozz/Algoritmia2/assets/90279053/58c70a4e-4896-4806-948f-0578110945cf)
