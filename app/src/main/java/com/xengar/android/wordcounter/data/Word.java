/*
 * Copyright (C) 2017 Angel Garcia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xengar.android.wordcounter.data;

/**
 * Word
 */
public class Word {
    private String name;
    private int number;

    public Word(String name, int number){
        this.name = name;
        this.number = number;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getNumber() { return  number; }
    public void setNumber(int number) {this.number = number; }
    public void incrementNumber() { this.number++; }
}
