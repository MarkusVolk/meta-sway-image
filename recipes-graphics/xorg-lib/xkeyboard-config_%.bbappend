do_install:append () {
    ln -sf ./us ${D}${datadir}/X11/xkb/symbols/en
}

