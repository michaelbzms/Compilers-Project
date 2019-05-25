@.mytest_vtable = global [0 x i8*] []
@.A_vtable = global [1 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*)]
@.B_vtable = global [2 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*), i8* bitcast (i32 (i8*, i32*, i32)* @B.bfunct to i8*)]
@.C_vtable = global [2 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*), i8* bitcast (i32 (i8*, i32*, i32)* @B.bfunct to i8*)]
@.D_vtable = global [3 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*), i8* bitcast (i32 (i8*, i32*, i32)* @B.bfunct to i8*), i8* bitcast (i1 (i8*)* @D.checkInheritanceAndArgs to i8*)]

declare i8* @calloc(i32, i32)
declare i32 @printf(i8*, ...)
declare void @exit(i32)

@_cint = constant [4 x i8] c"%d\0a\00"
@_cOOB = constant [15 x i8] c"Out of bounds\0a\00"

define void @print_int(i32 %i) {
    %_str = bitcast [4 x i8]* @_cint to i8*
    call i32 (i8*, ...) @printf(i8* %_str, i32 %i)
    ret void
}

define void @throw_oob() {
    %_str = bitcast [15 x i8]* @_cOOB to i8*
    call i32 (i8*, ...) @printf(i8* %_str)
    call void @exit(i32 1)
    ret void
}

define i32 @main() {
    %arr = alloca i32*
    %b = alloca i1
    %i = alloca i32
    ; assignment
    store i32 2, i32* %i
    %_0 = load i32, i32* %i
    call void (i32) @print_int(i32 %_0)
    %_1 = load i32, i32* %i
    %_2 = load i32, i32* %i
    %_3 = add i32 %_2, 1
    %_4 = icmp slt i32 %_1, %_3
    %_5 = zext i1 %_4 to i32
    call void (i32) @print_int(i32 %_5)
    ret i32 0
}

define i32* @A.afunct(i8* %this, i32* %.a1, i32* %.a2) {
    %a1 = alloca i32*
    %a2 = alloca i32*
    store i32* %.a1, i32** %a1
    store i32* %.a2, i32** %a2
    %_0 = load i32*, i32** %a1
    %_1 = add i32 1, 1
    %_2 = getelementptr i32, i32* %_0, i32 %_1
    %_3 = load i32, i32* %_2
    ; Array assignment
    %_4 = add i32 0, 1
    %_5 = load i32*, i32** %a2
    %_6 = getelementptr i32, i32* %_5, i32 %_4
    store i32 %_3, i32* %_6
    %_7 = load i32*, i32** %a2
    ; assignment
    store i32* %_7, i32** %a1
    %_8 = add i32 2, 1
    %_9 = call i8* @calloc(i32 4, i32 %_8)
    %_10 = bitcast i8* %_9 to i32*
    store i32 2, i32* %_10
    ret i32* %_10
}

define i32 @B.bfunct(i8* %this, i32* %.aarr, i32 %.i) {
    %i = alloca i32
    %aarr = alloca i32*
    store i32* %.aarr, i32** %aarr
    store i32 %.i, i32* %i
    %_0 = load i32*, i32** %aarr
    %_1 = load i32, i32* %i
    %_2 = add i32 %_1, 1
    %_3 = add i32 %_2, 1
    %_4 = getelementptr i32, i32* %_0, i32 %_3
    %_5 = load i32, i32* %_4
    ret i32 %_5
}

define i32* @C.afunct(i8* %this, i32* %.c1, i32* %.c2) {
    %res = alloca i32*
    %a = alloca i8*
    %c1 = alloca i32*
    %c2 = alloca i32*
    store i32* %.c1, i32** %c1
    store i32* %.c2, i32** %c2
    ; This is an object allocation of "mytest"
    %_0 = call i8* @calloc(i32 8, i32 1)
    %_1 = bitcast i8* %_0 to i8***
    %_2 = getelementptr [0 x i8*], [0 x i8*]* @.mytest_vtable, i32 0, i32 0
    store i8** %_2, i8*** %_1
    ; assignment
    store i8* %_0, i8** %a
    %_3 = load i32*, i32** %c1
    %_4 = add i32 0, 1
    %_5 = getelementptr i32, i32* %_3, i32 %_4
    %_6 = load i32, i32* %_5
    %_7 = load i32*, i32** %c2
    %_8 = add i32 1, 1
    %_9 = getelementptr i32, i32* %_7, i32 %_8
    %_10 = load i32, i32* %_9
    %_11 = icmp slt i32 %_6, %_10
    br i1 %_11, label %label0, label %label1
label0:
    %_12 = load i32*, i32** %c1
    ; assignment
    store i32* %_12, i32** %res
    br label %label2
label1:
    %_13 = load i32*, i32** %c2
    ; assignment
    store i32* %_13, i32** %res
    br label %label2
label2:
    %_14 = load i32*, i32** %res
    ret i32* %_14
}

define i1 @D.checkInheritanceAndArgs(i8* %this) {
    %arr = alloca i32*
    %i = alloca i32
    ; assignment
    store i32 1, i32* %i
    %_0 = load i32*, i32** %arr
    %_1 = add i32 1, 1
    %_2 = getelementptr i32, i32* %_0, i32 %_1
    %_3 = load i32, i32* %_2
    ; Method call
    %_10 = load i32*, i32** %arr
    %_11 = load i32*, i32** %arr
    %_4 = bitcast i8* %this to i8***
    %_5 = load i8**, i8*** %_4
    %_6 = getelementptr i8*, i8** %_5, i32 0
    %_7 = load i8*, i8** %_6
    %_8 = bitcast i8* %_7 to i32* (i8*, i32*, i32*)*
    %_9 = call i32* %_8(i8* %this, i32* %_11, i32* %_10)
    %_12 = add i32 2, 1
    %_13 = getelementptr i32, i32* %_9, i32 %_12
    %_14 = load i32, i32* %_13
    ; Array assignment
    %_15 = add i32 %_3, 1
    %_16 = load i32*, i32** %arr
    %_17 = getelementptr i32, i32* %_16, i32 %_15
    store i32 %_14, i32* %_17
    br label %label3
label3:
    %_18 = load i32, i32* %i
    %_19 = icmp slt i32 0, %_18
    br i1 %_19, label %label4, label %label5
label4:
    %_20 = load i32, i32* %i
    %_21 = add i32 %_20, 1
    ; assignment
    store i32 %_21, i32* %i
    %_22 = load i32*, i32** %arr
    ; assignment
    store i32* %_22, i32** %arr
    br label %label3
label5:
    ret i1 1
}

