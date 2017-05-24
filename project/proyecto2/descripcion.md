# Proyecto 2/(Examen 2 si todos aceptan)

* Fecha de entrega: **12 de Junio 2017**
* Basado en el proyecto 1. Los requerimiento para mostar la simulacion y elementos de entrada se mantienen
* Este proyecto es base para el proyecto 3

# Descripción:

La compañía de taxis karma opera en múltiples ciudades, y desea optimizar su funcionamiento y proveer a los taxis autónomos de rutas óptimas. 

Los clientes habitan en edificios de apartamentos.
 
El mundo tiene un dia de n minutos. Los habitantes trabajan un 30% del dia. Salen por la mañana y regresan por la noche. (Configurable)
 
Todos los vehículos son autónomos por lo cual la colision de vehiculos es 0. Sin embargo dependiendo de la hora existen congestionamientos.

## Entregables
### Codigo
  * Utilización de al menos 2 design patterns
  * Variables con nombres apropiados
  * Métodos con nombres y tamaño apropiados
  * Código indentado con alguno de los estándares que apliquen para el lenguaje 
  * Diseño de clases
  * Implementación de la máquina de estados o equivalente
  * Implementación del A*
     + Debe considerar los congestionamientos
  * La simulacion debe mostrar el congestionamiento en el mapa 

### Simulación de
* Multiples taxis
  * Configurable
* Ciclo de dîa. 
  * Los clientes hacia el trabajo y regresando del trabajo
La cantidad de habitantes es configurable (todos trabajan)
  * No todos salen al mismo tiempo al trabajo, se distribuye en un rango de tiempo, igual para el regreso a la casa (configurable)
  * Los clientes tienen un lugar de trabajo y tienen un lugar para vivir fijo. En otras palabras un cliente conoce donde trabaja y donde vive.
  * La idea es evitar un comportamiento totalmente aleatorio, si corremos la simulación debería poder verse un patrón de los clientes hacia el trabajo y del trabajo al lugar donde habitan
  * La cuadra debe indicar cuántos habitantes están en el edificio
* Carga de diferentes mapas
* La generación del mapa de congestionamiento por tiempo
  * Este debe generarse cada N unidades de tiempo y guardarse en un subdirectorio, es importante que el mapa indique el tiempo del día correspondiente
  * Los congestionamientos se dan cuando 1 o más taxis están en la misma calle, no hay collision
  * Procedimiento (los valores deben ser configurables):
     + Sección se define como calle, avenida o esquina  
     + Cada taxi en una sección incrementa el valor del congestionamiento en X cantidad (X = 0.25 parar el ejemplo)
     + Solo se incrementa si N o más taxis están en la misma sección. (N = 2 para el ejemplo)
     + Si no hay taxis en la seccion el valor del congestionamiento disminuye en D unidades por cada unidad de tiempo que pasa (D = -0.10)
     + Cuando un taxi entra a un congestionamiento registra el valor y se debe transformar en un tiempo de espera. Una vez que se registra el valor no importa si el congestionamiento crece el valor ya esta registrado. (Esto es para evitar que los taxis queden atrapadados)
     + Ejemplo para una sección:

<center>

| Tiempo        | Taxis         | Congestionamiento  |
| ------------- |:-------------:| -----:|
| 0 | 0 | 0 |
| 1 | 1 | 0 |
| 2 | 2 | 0.25 * 2 = 0.50 |
| 3 | 5 | 0.25 * 5 = 1.25 |
| 4 | 0 | 1.25 - 0.10 = 1.15 |
| 5 | 0 | 1.15 - 0.10 = 1.05 |
| 6 | 0 | 1.05 - 0.10 = 0.95|

</center>

* Usar **info.json** en esta carpeta para su proyecto. (Calificacion 0 si no esta o esta mal formado)

## Codigo de Honor - Proyecto 2 (Copiar y pegar en un archivo aparte)
Por este medio, yo: _______________________________________, como estudiante activo del Instituto Tecnologico de Costa Rica numero de carnet ________________, reconozco que con mi trabajo represento a la benemerita institucion y bajo esta responsabilidad y orgullo me comprometo a aplicar las normas mas altas de etica profesional en el desarrollo de proyecto programado numero uno del curso de Inteligencia Artificial. Doy fe con esta nota que no compartire codigo ni soluciones con mis compañeros, que no tratare de encontrar la solucion a los problemas por medios electronicos como internet, y que sere el unico en trabajar en la elaboracion del codigo, en mis reportes y en esta tarea programada, y en todos los problemas que en ella se encuentran. **Comprendo que la copia de codigo o programacion en conjunto tiene como resultado una evaluacion de 0 en el proyecto**

* Voy a plantear mis dudas como [Issues](https://github.com/luiskarlos/IA-117/issues) para que otros puedan participar y esclarecer dudas
  * El titulo del issue debe iniciar con:
  * **Proyecto 2** - 
  * Seguido de su comentario u observacion

  