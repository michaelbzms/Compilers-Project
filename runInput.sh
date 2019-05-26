
java Main inputs/$1.java && clang-4.0 -o inputs/$1 inputs/$1.ll && ./inputs/$1
