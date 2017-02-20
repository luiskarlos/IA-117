"use strict";

const State = require ('./state');
const Fsm = require ('./fsm');

/**
 * Estado Encendido
 */
class On extends State {
  accepts(event) {
    return event.msg === "on";
  }
  
  onEnter(eventEmitter, fsm) {
    fsm.owner().on();
    console.log(`${fsm.owner().id()} - prendiendo...`);
  }
  
  onUpdate(eventEmitter, fsm) {
    console.log(`${fsm.owner().id()} - prendida`);
  }
}

/**
 * Estado Apagado
 */
class Off extends State {
  
  accepts(event) {
    return event.msg === "off";
  }
  
  onEnter(eventEmitter, fsm) {
    fsm.owner().on();
    console.log(`${fsm.owner().id()} - apaganado....`);
  }
  
  onUpdate(eventEmitter, fsm) {
    console.log(`${fsm.owner().id()} - apagada`);
  }
}

/**
 * Los estados pueden ser de tipo Singleton.
 */
const STATES = [new On(), new Off()];

/**
 * Agente
 */
module.exports = class Ligth {
 
  constructor(id) {
    this._id = id;
    this._fsm = new Fsm(this, STATES);
  }

  id() {
    return this._id;
  }
  /**
   * No mucho q ver, pero si fuera un juego por ejemplo el agente estaria
   * alumbrando el area.
   */
  on() {
    this.status = "on";
  }

  off() {
    this.status = "off";
  }
}


