package main

import (
	"fmt"
	"unsafe"
)

// #include <stdlib.h>
// #include <string.h>
import "C"

//export greet
func greet(nameStr, langStr *byte) *byte {
	name := C.GoString((*C.char)(unsafe.Pointer(nameStr)))
	lang := C.GoString((*C.char)(unsafe.Pointer(langStr)))
	
	var greeting string
	switch lang {
	case "fr":
		greeting = fmt.Sprintf("Bonjour, %s!", name)
	case "de":
		greeting = fmt.Sprintf("Hallo, %s!", name)
	case "es":
		greeting = fmt.Sprintf("¡Hola, %s!", name)
	default:
		greeting = fmt.Sprintf("Hello, %s!", name)
	}
	return (*byte)(unsafe.Pointer(C.CString(greeting)))
}

func main() { }
