#!/bin/sh
echo "Converting Java bytecode to CLR dll..."
mono $IKVM_HOME/ikvmc.exe -nostdlib -debug -target:library -out:target/libs/dodgethecars.dll \
-r:$MONO_HOME/mscorlib.dll \
-r:$MONO_HOME/System.dll \
-r:$MONO_HOME/System.Core.dll \
-r:$MONO_HOME/System.Data.dll \
-r:$MONO_HOME/OpenTK.dll \
-r:$MONO_HOME/monotouch.dll \
-r:$MONO_HOME/Mono.Data.Sqlite.dll \
-r:$GDX_LIBS/gdx.dll \
-recurse:bin/*.class