/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jute.compiler;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * C++ Code generator front-end for Hadoop record I/O.
 */
class CGenerator {
    private String mName;
    private ArrayList<JFile> mInclFiles;
    private ArrayList<JRecord> mRecList;
    private final File outputDirectory;

    /** Creates a new instance of CppGenerator
     *
     * @param name possibly full pathname to the file
     * @param ilist included files (as JFile)
     * @param rlist List of records defined within this file
     * @param outputDirectory
     */
    CGenerator(String name, ArrayList<JFile> ilist, ArrayList<JRecord> rlist,
            File outputDirectory)
    {
        this.outputDirectory = outputDirectory;
        mName = (new File(name)).getName();
        mInclFiles = ilist;
        mRecList = rlist;
    }

    /**
     * Generate C++ code. This method only creates the requested file(s)
     * and spits-out file-level elements (such as include statements etc.)
     * record-level code is generated by JRecord.
     */
    void genCode() throws IOException {
        if (!outputDirectory.exists()) {
            if (!outputDirectory.mkdirs()) {
                new IOException("unable to create output directory "
                        + outputDirectory);
            }
        }

        try (FileWriter c = new FileWriter(new File(outputDirectory, mName + ".c"));
             FileWriter h = new FileWriter(new File(outputDirectory, mName + ".h"));
        ) {
            h.write("/**\n");
            h.write("* Licensed to the Apache Software Foundation (ASF) under one\n");
            h.write("* or more contributor license agreements.  See the NOTICE file\n");
            h.write("* distributed with this work for additional information\n");
            h.write("* regarding copyright ownership.  The ASF licenses this file\n");
            h.write("* to you under the Apache License, Version 2.0 (the\n");
            h.write("* \"License\"); you may not use this file except in compliance\n");
            h.write("* with the License.  You may obtain a copy of the License at\n");
            h.write("*\n");
            h.write("*     http://www.apache.org/licenses/LICENSE-2.0\n");
            h.write("*\n");
            h.write("* Unless required by applicable law or agreed to in writing, software\n");
            h.write("* distributed under the License is distributed on an \"AS IS\" BASIS,\n");
            h.write("* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
            h.write("* See the License for the specific language governing permissions and\n");
            h.write("* limitations under the License.\n");
            h.write("*/\n");
            h.write("\n");

            c.write("/**\n");
            c.write("* Licensed to the Apache Software Foundation (ASF) under one\n");
            c.write("* or more contributor license agreements.  See the NOTICE file\n");
            c.write("* distributed with this work for additional information\n");
            c.write("* regarding copyright ownership.  The ASF licenses this file\n");
            c.write("* to you under the Apache License, Version 2.0 (the\n");
            c.write("* \"License\"); you may not use this file except in compliance\n");
            c.write("* with the License.  You may obtain a copy of the License at\n");
            c.write("*\n");
            c.write("*     http://www.apache.org/licenses/LICENSE-2.0\n");
            c.write("*\n");
            c.write("* Unless required by applicable law or agreed to in writing, software\n");
            c.write("* distributed under the License is distributed on an \"AS IS\" BASIS,\n");
            c.write("* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
            c.write("* See the License for the specific language governing permissions and\n");
            c.write("* limitations under the License.\n");
            c.write("*/\n");
            c.write("\n");

            h.write("#ifndef __" + mName.toUpperCase().replace('.', '_') + "__\n");
            h.write("#define __" + mName.toUpperCase().replace('.', '_') + "__\n");

            h.write("#include \"recordio.h\"\n");
            for (Iterator<JFile> i = mInclFiles.iterator(); i.hasNext(); ) {
                JFile f = i.next();
                h.write("#include \"" + f.getName() + ".h\"\n");
            }
            // required for compilation from C++
            h.write("\n#ifdef __cplusplus\nextern \"C\" {\n#endif\n\n");

            c.write("#include <stdlib.h>\n"); // need it for calloc() & free()
            c.write("#include \"" + mName + ".h\"\n\n");

            for (Iterator<JRecord> i = mRecList.iterator(); i.hasNext(); ) {
                JRecord jr = i.next();
                jr.genCCode(h, c);
            }

            h.write("\n#ifdef __cplusplus\n}\n#endif\n\n");
            h.write("#endif //" + mName.toUpperCase().replace('.', '_') + "__\n");
        }
    }
}
