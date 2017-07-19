#!/usr/bin/env python2.7

# Copyright 2017 Yahoo Inc.
#
# Licensed under the terms of the Apache license. Please see LICENSE.md
# file distributed with this work for terms.

import os
import subprocess

# could be in python if I weren't lazy
cmd = [ 'find', '.', '-type', 'f', '(', '-name', '*.java',
       '-o', '-name', '*.groovy', ')']

for path in subprocess.check_output(cmd).splitlines():
    if os.path.exists(path) and 'target' not in path:
        with open(path) as fh:
            contents = fh.readlines()
            if '// Copyright 20' not in contents[0]:
                print 'updating %s' % path

                contents.insert(0, '// Copyright 2017 Yahoo Inc.\n')
                contents.insert(1, '// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.\n')

        with open(path, 'w') as fh:
            fh.write(''.join(contents))
