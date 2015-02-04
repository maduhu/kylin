/*
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

package org.apache.kylin.job.hadoop.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.kylin.common.KylinConfig;
import org.apache.kylin.common.util.LocalFileMetadataTestCase;

/**
 * Created by honma on 8/20/14.
 */
public class HdfsOpsTest extends LocalFileMetadataTestCase {

    FileSystem fileSystem;

    @Before
    public void setup() throws Exception {

        this.createTestMetadata();

        Configuration hconf = new Configuration();

        fileSystem = FileSystem.get(hconf);
    }

    @Test
    public void TestPath() throws IOException {
        String hdfsWorkingDirectory = KylinConfig.getInstanceFromEnv().getHdfsWorkingDirectory();
        Path coprocessorDir = new Path(hdfsWorkingDirectory, "test");
        fileSystem.mkdirs(coprocessorDir);

        Path newFile = new Path(coprocessorDir, "test_file");
        newFile = newFile.makeQualified(fileSystem.getUri(), null);
        FSDataOutputStream stream = fileSystem.create(newFile);
        stream.write(new byte[] { 0, 1, 2 });
        stream.close();
    }

    @After
    public void after() throws Exception {
        this.cleanupTestMetadata();
    }
}
