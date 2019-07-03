/*
 * Copyright (C) 2018 Reginildo Sousa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.reginildo.tomateapp;

import java.io.Serializable;

class Tomate implements Serializable {
    private int ciclosTime;
    private int longBreakTime;
    private int pomodoroTime;
    private int shortBreakTime;
    private int beepAlarm;

    Tomate(int ciclosTime, int pomodoroTime, int longBreakTime, int shortBreakTime, int beepAlarm) {
        setCiclosTime(ciclosTime);
        setLongBreakTime(longBreakTime);
        setPomodoroTime(pomodoroTime);
        setShortBreakTime(shortBreakTime);
    }

    public void setBeepAlarm(int beepAlarm) { this.beepAlarm = beepAlarm; }

    public int getBeepAlarm() { return beepAlarm; }

    int getLongBreakTime() {
        return longBreakTime;
    }

    void setLongBreakTime(int timeOfLongBreak) {
        // todo teste aqui
        //longBreakTime = timeOfLongBreak * 60 * 1000; <- esta é a linha correta
        longBreakTime = timeOfLongBreak * 1000;
    }

    int getPomodoroTime() {
        return pomodoroTime;
    }

    void setPomodoroTime(int timeOfPomodoro) {
        //todo teste aqui
//        pomodoroTime = timeOfPomodoro * 60 * 1000; <- esta é a linha correta
        pomodoroTime = timeOfPomodoro * 1000;
    }

    int getShortBreakTime() {
        return shortBreakTime;
    }

    void setShortBreakTime(int timeOfShortBreak) {
        // todo teste aqui
//        shortBreakTime = timeOfShortBreak * 60 * 1000; <- esta é a linha correta
        shortBreakTime = timeOfShortBreak * 1000;
    }

    int getCiclosTime() {
        return ciclosTime;
    }

    void setCiclosTime(int timeOfCclos) {
        ciclosTime = timeOfCclos;
    }

}
