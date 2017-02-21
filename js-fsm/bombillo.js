"use strict";

const State = require('./state')
const Fsm = require('./fsm')
const eventEmitter = require('./event-emiter')

var inc = 0;

class Apagado extends State {
  accepts(event) {
    console.log("[Apagado]" + JSON.stringify(event));
    return event.msg === "apagar";
  }

  onEnter(eventEmitter, fsm) {
    fsm.owner().apagar();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

class Encendido extends State {
  accepts(event) {
    console.log("[Encendido]" + JSON.stringify(event));
    return event.msg === "encender";
  }

  onEnter(eventEmitter, fsm) {
    console.log("[Encendido] onEnter" );
    fsm.owner().prender();
  }

  onUpdate(eventEmitter, fsm) {
    fsm.owner().show();
  }
}

const states = [new Apagado(), new Encendido()];

class Bombillo {
  constructor(id) {
    this._id = id;
    this._estado = "apagado";
    const miFsm = new Fsm(this, states);
    eventEmitter.register(miFsm);
  }

  id() {
    return this._id;
  }

  apagar() {
    this._estado = "apagado";
  }

  prender() {
    this._estado = "encendido";
  }

  show() {
    if (this._estado === "encendido") {
      console.log(`[Bombillo] ${this.id()} + brillar`);
    } else {
      console.log(`[Bombillo] ${this.id()}`);
    }
  }
}

module.exports = Bombillo