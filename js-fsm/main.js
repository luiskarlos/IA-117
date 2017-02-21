const eventEmiter = require ('./event-emiter');
const Bombillo = require ('./bombillo');

/**
 * Ciclo principal
 */
setInterval(() => {
  eventEmiter.update();
  eventEmiter.send("update");
}, 500);

const b1 = new Bombillo("b1");
//eventEmiter.send("apagar");

var flip = 0;
setInterval(() => {
  console.log(`flip: ${flip}`);
  if (flip === 0) {
    eventEmiter.send("encender");
    flip = 1;
  } else {
    eventEmiter.send("apagar");
    flip = 0;
  }
}, 2000);