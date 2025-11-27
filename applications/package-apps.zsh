#!/bin/zsh
echo "Packaging the Luego applications"

function runCommand() {
    for d in ./*/ ; do /bin/zsh -c "(cd "$d" && echo && echo '================================' && echo 'PACKAGING' "$d" && echo && "$@")"; done
}

runCommand "mvn exec:java@luego-compiler -Dexec.args=\"app package\""