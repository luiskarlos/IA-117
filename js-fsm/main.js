const eventEmiter = require ('./event-emiter');
const Light = require ('./Light');

/**
 * Ciclo principal
 */
setInterval(() => {
  eventEmiter.update();
  eventEmiter.send("update");
}, 100);
