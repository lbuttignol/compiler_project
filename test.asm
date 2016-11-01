# begin program
.file test.asm
.glob main
.type	main, @function
# begin class
Person:
movq $0, -0(%rbp)
setYears:
enter $2,$0
mov -0(%rbp), %r10
mov %r10, -0(%rbp)
leave
ret

getYears:
enter $1,$0
mov -0(%rbp), %rax
leave
ret

# begin class
Mom:
movq $0, -1(%rbp)
movq $0, -2(%rbp)
setYears:
enter $2,$0
mov -0(%rbp), %r10
mov %r10, -0(%rbp)
leave
ret

getYears:
enter $1,$0
mov -0(%rbp), %rax
leave
ret

setChilds:
enter $2,$0
mov -0(%rbp), %r10
mov %r10, -0(%rbp)
leave
ret

getChilds:
enter $1,$0
mov -0(%rbp), %rax
leave
ret

# begin class
Main:
movq $0, -1(%rbp)
movq $0, -2(%rbp)
movq $0, -3(%rbp)
method:
enter $12,$0
movq $0, -1(%rbp)
mov -0(%rbp), %r10
mov -0(%rbp), %r11
add %r11, %r10
mov %r10, %rax
mov %rax, -3(%rbp)
mov -3(%rbp), %r10
mov %r10, -0(%rbp)
mov -0(%rbp), %r10
imul $-1, %r10
mov %r11, %rax
mov %rax, -0(%rbp)
mov -4(%rbp), %r10
mov %r10, -0(%rbp)
mov -0(%rbp), %r10
mov -0(%rbp), %r11
sub %r11, %r10
mov %r10, %rax
mov %rax, -5(%rbp)
mov -5(%rbp), %r10
mov %r10, -0(%rbp)
mov -0(%rbp), %r10
mov -0(%rbp), %r11
add %r11, %r10
mov %r10, %rax
mov %rax, -6(%rbp)
mov -6(%rbp), %rax
mov -0(%rbp), %r10
idiv %r10
mov %rax, -7(%rbp)
mov -7(%rbp), %r10
mov %r10, -0(%rbp)
mov $1, -8(%rbp)
mov -8(%rbp), %r10
mov $1, %rax
cmp $1, %r10
cmove $0, %rax
mov -9(%rbp), %r10
mov %r10, -0(%rbp)
mov -10(%rbp), %r10
mov %r10, -0(%rbp)
mov -0(%rbp), %r10
mov -0(%rbp), %r11
sub %r11, %r10
mov %r10, %rax
mov %rax, -11(%rbp)
mov -11(%rbp), %rax
leave
ret

method1:
enter $13,$0
movq $0, -3(%rbp)
mov $0, -6(%rbp)
mov -6(%rbp), %r10
mov %r10, -0(%rbp)
mov -7(%rbp), %r10
mov %r10, -0(%rbp)
mov $32, -8(%rbp)
mov -8(%rbp), %rdi
call setYears
mov $5, -9(%rbp)
mov -9(%rbp), %rdi
call setChilds
mov $43, -10(%rbp)
mov -10(%rbp), %rdi
call setYears
# begin if 
mov -0(%rbp),%r11
cmp $0, %r11
jne ELSEIF0
mov $0, -11(%rbp)
mov -11(%rbp), %r10
mov %r10, -0(%rbp)
jmp ENDIF0
ELSEIF0: 
mov $1, -12(%rbp)
mov -12(%rbp), %r10
mov %r10, -0(%rbp)
ENDIF0: 
mov -0(%rbp), %rax
leave
ret

main:
enter $1,$0
leave
ret

# end program