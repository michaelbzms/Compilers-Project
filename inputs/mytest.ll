@.mytest_vtable = global [0 x i8*] []
@.A_vtable = global [1 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*)]
@.B_vtable = global [2 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @A.afunct to i8*), i8* bitcast (i32 (i8*, i32*, i32)* @B.bfunct to i8*)]
@.C_vtable = global [2 x i8*] [i8* bitcast (i32* (i8*, i32*, i32*)* @C.afunct to i8*), i8* bitcast (i32 (i8*, i32*, i32)* @B.bfunct to i8*)]
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
    %b = alloca i8*
    %d = alloca i8*
    %bull = alloca i1
     ; array allocation
    %_0 = add i32 102, 1
    %_1 = call i8* @calloc(i32 4, i32 %_0)
    %_2 = bitcast i8* %_1 to i32*
    store i32 102, i32* %_2
    ; assignment
    store i32* %_2, i32** %arr
    ; This is an object allocation of "C"
    %_3 = call i8* @calloc(i32 32, i32 1)
    %_4 = bitcast i8* %_3 to i8***
    %_5 = getelementptr [2 x i8*], [2 x i8*]* @.C_vtable, i32 0, i32 0
    store i8** %_5, i8*** %_4
    ; assignment
    store i8* %_3, i8** %b
    ; This is an object allocation of "D"
    %_6 = call i8* @calloc(i32 32, i32 1)
    %_7 = bitcast i8* %_6 to i8***
    %_8 = getelementptr [3 x i8*], [3 x i8*]* @.D_vtable, i32 0, i32 0
    store i8** %_8, i8*** %_7
    ; assignment
    store i8* %_6, i8** %d
    ; Method call
    %_9 = load i8*, i8** %b
    %_16 = load i32*, i32** %arr
    %_17 = load i32*, i32** %arr
    %_10 = bitcast i8* %_9 to i8***
    %_11 = load i8**, i8*** %_10
    %_12 = getelementptr i8*, i8** %_11, i32 0
    %_13 = load i8*, i8** %_12
    %_14 = bitcast i8* %_13 to i32* (i8*, i32*, i32*)*
    %_15 = call i32* %_14(i8* %_9, i32* %_17, i32* %_16)
    ; assignment
    store i32* %_15, i32** %arr
    ; Method call
    %_18 = load i8*, i8** %d
    %_25 = load i32*, i32** %arr
    %_26 = load i32*, i32** %arr
    %_19 = bitcast i8* %_18 to i8***
    %_20 = load i8**, i8*** %_19
    %_21 = getelementptr i8*, i8** %_20, i32 0
    %_22 = load i8*, i8** %_21
    %_23 = bitcast i8* %_22 to i32* (i8*, i32*, i32*)*
    %_24 = call i32* %_23(i8* %_18, i32* %_26, i32* %_25)
    ; assignment
    store i32* %_24, i32** %arr
    ; This is an object allocation of "B"
    %_27 = call i8* @calloc(i32 24, i32 1)
    %_28 = bitcast i8* %_27 to i8***
    %_29 = getelementptr [2 x i8*], [2 x i8*]* @.B_vtable, i32 0, i32 0
    store i8** %_29, i8*** %_28
    ; assignment
    store i8* %_27, i8** %b
    ; Method call
    %_30 = load i8*, i8** %b
    %_37 = load i32*, i32** %arr
    %_38 = load i32*, i32** %arr
    %_31 = bitcast i8* %_30 to i8***
    %_32 = load i8**, i8*** %_31
    %_33 = getelementptr i8*, i8** %_32, i32 0
    %_34 = load i8*, i8** %_33
    %_35 = bitcast i8* %_34 to i32* (i8*, i32*, i32*)*
    %_36 = call i32* %_35(i8* %_30, i32* %_38, i32* %_37)
    ; assignment
    store i32* %_36, i32** %arr
    ; Method call
    %_39 = load i8*, i8** %d
    %_40 = bitcast i8* %_39 to i8***
    %_41 = load i8**, i8*** %_40
    %_42 = getelementptr i8*, i8** %_41, i32 2
    %_43 = load i8*, i8** %_42
    %_44 = bitcast i8* %_43 to i1 (i8*)*
    %_45 = call i1 %_44(i8* %_39)
    ; assignment
    store i1 %_45, i1* %bull
    ret i32 0
}

define i32* @A.afunct(i8* %this, i32* %.a1, i32* %.a2) {
    %a1 = alloca i32*
    %a2 = alloca i32*
    store i32* %.a1, i32** %a1
    store i32* %.a2, i32** %a2
    %_0 = load i32*, i32** %a1
     ; array primary expression
    %_1 = load i32, i32* %_0
    %_2 = icmp ult i32 1, %_1
    br i1 %_2, label %label1, label %label0
label0:
    call void @throw_oob()
    br label %label2
label1:
    %_3 = add i32 1, 1
    %_4 = getelementptr i32, i32* %_0, i32 %_3
    %_5 = load i32, i32* %_4
    br label %label2
label2:
    %_6 = load i32*, i32** %a2
    ; array assignment
    %_7 = load i32, i32* %_6
    %_8 = icmp ult i32 0, %_7
    br i1 %_8, label %label4, label %label3
label3:
    call void @throw_oob()
    br label %label5
label4:
    ; Array assignment
    %_9 = add i32 0, 1
    %_10 = getelementptr i32, i32* %_6, i32 %_9
    store i32 %_5, i32* %_10
    br label %label5
label5:
    %_11 = load i32*, i32** %a2
    ; assignment
    store i32* %_11, i32** %a1
    call void (i32) @print_int(i32 42)
     ; array allocation
    %_12 = add i32 2, 1
    %_13 = call i8* @calloc(i32 4, i32 %_12)
    %_14 = bitcast i8* %_13 to i32*
    store i32 2, i32* %_14
    ret i32* %_14
}

define i32 @B.bfunct(i8* %this, i32* %.aarr, i32 %.i) {
    %i = alloca i32
    %aarr = alloca i32*
    store i32* %.aarr, i32** %aarr
    store i32 %.i, i32* %i
    %_0 = load i32*, i32** %aarr
    %_1 = load i32, i32* %i
    %_2 = add i32 %_1, 1
     ; array primary expression
    %_3 = load i32, i32* %_0
    %_4 = icmp ult i32 %_2, %_3
    br i1 %_4, label %label1, label %label0
label0:
    call void @throw_oob()
    br label %label2
label1:
    %_5 = add i32 %_2, 1
    %_6 = getelementptr i32, i32* %_0, i32 %_5
    %_7 = load i32, i32* %_6
    br label %label2
label2:
    ret i32 %_7
}

define i32* @C.afunct(i8* %this, i32* %.c1, i32* %.c2) {
    %res = alloca i32*
    %a = alloca i8*
    %c1 = alloca i32*
    %c2 = alloca i32*
    store i32* %.c1, i32** %c1
    store i32* %.c2, i32** %c2
    call void (i32) @print_int(i32 102)
    ; This is an object allocation of "mytest"
    %_0 = call i8* @calloc(i32 8, i32 1)
    %_1 = bitcast i8* %_0 to i8***
    %_2 = getelementptr [0 x i8*], [0 x i8*]* @.mytest_vtable, i32 0, i32 0
    store i8** %_2, i8*** %_1
    ; assignment
    store i8* %_0, i8** %a
    %_3 = load i32*, i32** %c1
     ; array primary expression
    %_4 = load i32, i32* %_3
    %_5 = icmp ult i32 0, %_4
    br i1 %_5, label %label1, label %label0
label0:
    call void @throw_oob()
    br label %label2
label1:
    %_6 = add i32 0, 1
    %_7 = getelementptr i32, i32* %_3, i32 %_6
    %_8 = load i32, i32* %_7
    br label %label2
label2:
    %_9 = load i32*, i32** %c2
     ; array primary expression
    %_10 = load i32, i32* %_9
    %_11 = icmp ult i32 1, %_10
    br i1 %_11, label %label4, label %label3
label3:
    call void @throw_oob()
    br label %label5
label4:
    %_12 = add i32 1, 1
    %_13 = getelementptr i32, i32* %_9, i32 %_12
    %_14 = load i32, i32* %_13
    br label %label5
label5:
    %_15 = icmp slt i32 %_8, %_14
    br i1 %_15, label %label6, label %label7
label6:
    %_16 = load i32*, i32** %c1
    ; assignment
    store i32* %_16, i32** %res
    br label %label8
label7:
    %_17 = load i32*, i32** %c2
    ; assignment
    store i32* %_17, i32** %res
    br label %label8
label8:
    %_18 = load i32*, i32** %res
    ret i32* %_18
}

define i1 @D.checkInheritanceAndArgs(i8* %this) {
    %arr = alloca i32*
    %i = alloca i32
    call void (i32) @print_int(i32 69)
    ; assignment
    store i32 1, i32* %i
     ; array allocation
    %_0 = add i32 2, 1
    %_1 = call i8* @calloc(i32 4, i32 %_0)
    %_2 = bitcast i8* %_1 to i32*
    store i32 2, i32* %_2
    ; assignment
    store i32* %_2, i32** %arr
    %_3 = load i32*, i32** %arr
    ; array assignment
    %_4 = load i32, i32* %_3
    %_5 = icmp ult i32 0, %_4
    br i1 %_5, label %label1, label %label0
label0:
    call void @throw_oob()
    br label %label2
label1:
    ; Array assignment
    %_6 = add i32 0, 1
    %_7 = getelementptr i32, i32* %_3, i32 %_6
    store i32 1, i32* %_7
    br label %label2
label2:
    %_8 = load i32*, i32** %arr
    ; array assignment
    %_9 = load i32, i32* %_8
    %_10 = icmp ult i32 1, %_9
    br i1 %_10, label %label4, label %label3
label3:
    call void @throw_oob()
    br label %label5
label4:
    ; Array assignment
    %_11 = add i32 1, 1
    %_12 = getelementptr i32, i32* %_8, i32 %_11
    store i32 0, i32* %_12
    br label %label5
label5:
    %_13 = load i32*, i32** %arr
    %_14 = load i32, i32* %_13
    call void (i32) @print_int(i32 %_14)
    br label %label6
label6:
    %_15 = load i32, i32* %i
    %_16 = icmp slt i32 %_15, 2
    br i1 %_16, label %label7, label %label8
label7:
    %_17 = load i32, i32* %i
    %_18 = add i32 %_17, 1
    ; assignment
    store i32 %_18, i32* %i
    ; short-circuiting "&&"
    %_21 = load i32*, i32** %arr
     ; array primary expression
    %_22 = load i32, i32* %_21
    %_23 = icmp ult i32 1, %_22
    br i1 %_23, label %label13, label %label12
label12:
    call void @throw_oob()
    br label %label14
label13:
    %_24 = add i32 1, 1
    %_25 = getelementptr i32, i32* %_21, i32 %_24
    %_26 = load i32, i32* %_25
    br label %label14
label14:
    %_27 = load i32*, i32** %arr
     ; array primary expression
    %_28 = load i32, i32* %_27
    %_29 = icmp ult i32 0, %_28
    br i1 %_29, label %label16, label %label15
label15:
    call void @throw_oob()
    br label %label17
label16:
    %_30 = add i32 0, 1
    %_31 = getelementptr i32, i32* %_27, i32 %_30
    %_32 = load i32, i32* %_31
    br label %label17
label17:
    %_33 = icmp slt i32 %_26, %_32
    br i1 %_33, label %label10, label %label9
label9:
    %_19 = and i1 0, 0
    br label %label11
label10:
    ; Method call
    %_34 = bitcast i8* %this to i8***
    %_35 = load i8**, i8*** %_34
    %_36 = getelementptr i8*, i8** %_35, i32 2
    %_37 = load i8*, i8** %_36
    %_38 = bitcast i8* %_37 to i1 (i8*)*
    %_39 = call i1 %_38(i8* %this)
    br label %label11
label11:
    %_20 = phi i1 [%_19, %label9], [%_39, %label10]
    br i1 %_20, label %label18, label %label19
label18:
    call void (i32) @print_int(i32 44)
    br label %label20
label19:
    call void (i32) @print_int(i32 22)
    br label %label20
label20:
    %_40 = load i32*, i32** %arr
    ; assignment
    store i32* %_40, i32** %arr
    br label %label6
label8:
    ret i1 1
}

