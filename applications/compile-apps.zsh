#!/bin/zsh
echo "Compiling the Luego applications"

function runCommand() {
    for d in ./*/ ; do /bin/zsh -c "(cd "$d" && echo && echo '================================' && echo 'COMPILING' "$d" && echo && "$@")"; done
}

runCommand "mvn exec:java@luego-compiler -Dexec.args=\"app compile\""