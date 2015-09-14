/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.mcessence.essence.cmd_arguments.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParseResults {
    public boolean success = true;
    private List<ArgumentParseResult> parseResults = new ArrayList<ArgumentParseResult>();
    private String[] args = null;
    private List<String> modifiers = new ArrayList<String>();
    private Map<String, Object> optionalArgs = new HashMap<String, Object>();

    public ArgumentParseResult getValue(int argumentIndex) {
        if (parseResults.size() >= argumentIndex) {
            return parseResults.get(argumentIndex);
        }
        return null;
    }

    public void setValue(int argumentIndex, ArgumentParseResult parseResult) {
        parseResults.add(argumentIndex, parseResult);
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void addModifier(String modifier) {
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier.toLowerCase());
        }
    }

    public boolean hasModifier(String modifier) {
        return modifiers.contains(modifier.toLowerCase());
    }

    public void addOptionalArg(String arg, Object value) {
        optionalArgs.put(arg.toLowerCase(), value);
    }

    public boolean hasOptionalArg(String arg) {
        return optionalArgs.containsKey(arg.toLowerCase());
    }

    public Object getOptionalArg(String arg) {
        if (optionalArgs.containsKey(arg.toLowerCase())) {
            return optionalArgs.get(arg.toLowerCase());
        }
        return "";
    }
}
