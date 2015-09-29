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

/**
 * An instance of this will be returned when parsing command arguments.
 * All values will be stored in this result including modifiers and optional arguments etc.
 * Before doing anything with this result you should always check if the parsing was successful or not.
 * If not you should return out of the command and don't do anything.
 * Example usage:
 * ArgumentParseResults result = parseArgs(this, sender, args);
 * if (!result.success) {
 *     return true;
 * }
 * result.getArg(0) would return the value specified in the first argument.
 */
public class ArgumentParseResults {
    public boolean success = true;
    private List<Object> argument = new ArrayList<Object>();
    private String[] args = null;
    private List<String> modifiers = new ArrayList<String>();
    private Map<String, Object> optionalArgs = new HashMap<String, Object>();


    /**
     * Set the command argument parse result value for the given argument.
     * This is used by the parser and it's not recommended to manually call this unless you know what you're doing.
     * @param argumentIndex The index for the argument to set.
     * @param value A object which is the value of the argument.
     */
    public void setArg(int argumentIndex, Object value) {
        argument.add(argumentIndex, value);
    }

    /**
     * Get an argument value from the parsed command arguments.
     * The specified index needs to match with the order command arguments are defined in the constructor.
     * If an argument is required the value for this argument will always be set and it wont ever be null so then it's safe to cast it directly.
     * However, if the argument is optional or required console the value can be null.
     * For example if the first argument is a PlayerArgument which is REQUIRED_CONSOLE you would do something like this to get the player.
     * result.getArg(0) == null ? (Player)sender : (Player)result.getArg(0)
     * @param argumentIndex The index for the argument to retrieve. (Order of argument definition in the constructor!)
     * @return A Object containing the value.
     */
    public Object getArg(int argumentIndex) {
        if (argument.size() >= argumentIndex) {
            return argument.get(argumentIndex);
        }
        return null;
    }


    /**
     * Set the command argument array.
     * This is used by the parser and it's not recommended to manually call this unless you know what you're doing.
     * @param args A modified array of arguments to set.
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     * Get a modifier list of arguments without modifiers and optional arguments.
     * The parser will strip out all modifiers and arguments and set the new args in this result instance.
     * When working with args in the command it's important to do args = result.getArgs(); after parsing.
     * @return String array with a modified argument list.
     */
    public String[] getArgs() {
        return args;
    }


    /**
     * Adds the given modifier.
     * This is used by the parser and it's not recommended to manually call this unless you know what you're doing.
     * So basically when you specify "-s" in the command the parser would call addModifier("-s")
     * @param modifier The modifier key to add like "-s"
     */
    public void addModifier(String modifier) {
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier.toLowerCase());
        }
    }

    /**
     * Checks if the given modifier was specified in the command or not.
     * @param modifier The modifier key like "-s"
     * @return If it was specified or not.
     */
    public boolean hasModifier(String modifier) {
        return modifiers.contains(modifier.toLowerCase());
    }


    /**
     * Sets the value for the given optional argument.
     * This is used by the parser and it's not recommended to manually call this unless you know what you're doing.
     * @param arg The key for the argument to set.
     * @param value The value to set for the argument. (This should never be set to null)
     */
    public void addOptionalArg(String arg, Object value) {
        optionalArgs.put(arg.toLowerCase(), value);
    }

    /**
     * Get the specified value for the optional arg.
     * If there was no value specified and it has a default the value will be the default value.
     * If there is no default value it would fail the command.
     * This means you can directly cast the value to the argument type.
     * For example if you create a BoolArg with "test" you can do (Boolean)result.getOptionalArg("test");
     * @param arg The key for the argument to return.
     * @return Object containing the value specified or default value.
     */
    public Object getOptionalArg(String arg) {
        if (optionalArgs.containsKey(arg.toLowerCase())) {
            return optionalArgs.get(arg.toLowerCase());
        }
        return "";
    }
}
