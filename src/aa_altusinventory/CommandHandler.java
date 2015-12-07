/******************************************************************************
* Title: CommandHandler.java
* Author: Hunter Schoonover
* Date: 12/06/15
*
* Purpose:
*
* This Interface provides methods used to transfer commands between objects.
*
*/

//-----------------------------------------------------------------------------

package aa_altusinventory;

public interface CommandHandler {

    public void init();
    
    public void performCommand(String pCommand);

}
