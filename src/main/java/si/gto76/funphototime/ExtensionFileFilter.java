/*
 * ProGuard -- shrinking, optimization, obfuscation, and preverification
 *             of Java bytecode.
 *
 * Copyright (c) 2002-2012 Eric Lafortune (eric@graphics.cornell.edu)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package si.gto76.funphototime;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * This <code>FileFilter</code> accepts files that end in one of the given
 * extensions.
 * 
 * @author Eric Lafortune
 */
public final class ExtensionFileFilter extends FileFilter {
	private final String description;
	private final String[] extensions;
	
    static final String[] pngExtensions = {".png"};
    public static final ExtensionFileFilter png = new ExtensionFileFilter("png", pngExtensions);
    static final String[] gifExtensions = {".gif"};
    public static final ExtensionFileFilter gif = new ExtensionFileFilter("gif", gifExtensions);
    static final String[] bmpExtensions = {".bmp"};
    public static final ExtensionFileFilter bmp = new ExtensionFileFilter("bmp", bmpExtensions);
    static final String[] jpgExtensions = {".jpg", ".jpeg"};
    public static final ExtensionFileFilter jpg = new ExtensionFileFilter("jpg", jpgExtensions);

    public static final ExtensionFileFilter[] all =  {png, gif, jpg, bmp};
    
    /**
     * Returns matching filter for a filename, null if none match.
     */
    public static ExtensionFileFilter getFilter(String fileName) {
    	for (ExtensionFileFilter filter : all) {
    		if (filter.accept(fileName)) {
    			return filter;
    		}
    	}
    	return null;
    }
    
	/**
	 * Creates a new ExtensionFileFilter.
	 * 
	 * @param description
	 *            a description of the filter.
	 * @param extensions
	 *            an array of acceptable extensions.
	 */
	public ExtensionFileFilter(String description, String[] extensions) {
		this.description = description;
		this.extensions = extensions;
	}

	// Implemntations for FileFilter

	public String getDescription() {
		return description;
	}

	public boolean accept(String fileName) {
		for (int index = 0; index < extensions.length; index++) {
			if (fileName.endsWith(extensions[index])) {
				return true;
			}
		}
		return false;		
	}
	
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String fileName = file.getName().toLowerCase();
		return accept(fileName);
	}
}
