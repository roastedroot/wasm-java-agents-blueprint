use std::ffi::{CStr, CString};
use std::os::raw::c_char;

#[no_mangle]
pub extern "C" fn greet(lang: *const c_char, name: *const c_char) -> *mut c_char {
    let lang_str = unsafe { CStr::from_ptr(lang).to_string_lossy() };
    let name_str = unsafe { CStr::from_ptr(name).to_string_lossy() };

    let result = match lang_str.as_ref() {
        "fr" => format!("Bonjour, {}!", name_str),
        "de" => format!("Hallo, {}!", name_str),
        "en" => format!("Hello, {}!", name_str),
        "es" => format!("¡Hola, {}!", name_str),
        _ => format!("Hello, {}!", name_str),
    };

    match CString::new(result) {
        Ok(cstr) => cstr.into_raw(),
        Err(_) => std::ptr::null_mut(),
    }
}

#[no_mangle]
pub extern "C" fn malloc(size: usize) -> *mut u8 {
    unsafe { std::alloc::alloc(std::alloc::Layout::from_size_align_unchecked(size, 1)) }
}
