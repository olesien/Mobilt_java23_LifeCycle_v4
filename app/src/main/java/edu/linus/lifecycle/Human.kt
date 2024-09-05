package edu.linus.lifecycle

class Human(var name: String, override var prompt: String) : AI {
    var age:Int = 5
        get() {
            return age;
        }

                fun getAll(): Int {
                    return age;
                };

}