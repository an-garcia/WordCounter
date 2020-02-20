/*
 * Copyright (C) 2017 Angel Newton
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
 * Count
 */
public class Count {
    private long cWords = 0;
    private long cCharacters = 0;
    private long cSpaces = 0;

    /** Constructor */
    public Count() {
    }

    /*** Constructor */
    public Count(long words, long characters, long spaces) {
        this.cCharacters = characters;
        this.cSpaces = spaces;
        this.cWords = words;
    }

    public long getWords(){ return  cWords; }
    public void setWords(long words) { this.cWords = words; }

    public long getCharacters() { return  cCharacters; }
    public void setCharacters(long characters) { this.cCharacters = characters; }

    public long getSpaces() { return cSpaces; }
    public void setSpaces(long spaces) { this.cSpaces = spaces; }
}
