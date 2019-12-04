definition(
    name: "My First SmartApp",
    namespace: "mygithubusername",
    author: "Peter Gregory",
    description: "This is my first SmartApp. Woot!",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
    section("Turn off when there's been no movement for") {
        input "minutes", "number", required: true, title: "Minutes?"
    }
    section("Turn on this light") {
        input "theswitch", "capability.switch", required: true
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
    subscribe(theswitch, "switch.on", switchOnHandler)
  //  subscribe(theswitch, "switch.off", switchOffHandler)
}

def motionDetectedHandler(evt) {
    log.debug "motionDetectedHandler called: $evt"
    theswitch.on()
}

def switchOnHandler(evt) {
    log.debug "switchOnHandler called: $evt"
    runIn(minutes, turnOff)
}

def switchOffHandler(evt) {
    log.debug "switchOnHandler called: $evt"
    runIn(minutes, turnOn)
}

def turnOff() {
    log.debug "In turn off method"

    theswitch.off()
    runIn(minutes, turnOn)
}


def turnOn() {
    log.debug "In turn on method"

    theswitch.on()
    runIn(minutes, turnOff)
}
