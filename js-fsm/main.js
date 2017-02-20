const eventEmiter = require ('./event-emiter');
const Light = require ('./Light');

/**
 * Ciclo principal
 */
setInterval(() => {
  eventEmiter.update();
  eventEmiter.send("update");
}, 100);

/**
 * Crear una nuevo bombillo. El agente segrega al sistema solo
 */
new Light("l1");
new Light("l2");

/**
 * Condigo de prueba.
 */
var status = false;
setInterval(() => {
  if (status) {
    eventEmiter.send("off");
    status = false;
  } else {
    eventEmiter.send("on");
    status = true;
  }
  eventEmiter.send("off", "l2");
}, 1000)