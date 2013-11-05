#!/bin/bash

APP=GroovyGradle
VERSION=1.0
RPM_BUILDROOT=$PWD/build/rpm
RPM_SOURCE=$PWD/src/main/rpm

APPVERSION=$APP-$VERSION

function throw_error()
{
    RESULT = $1
    ERROR_MESSAGE = $2

    if [ $RESULT -ne 0 ]; then
        echo "$ERROR_MESSAGE"
        exit 1
    fi
}

REVISION=$1
if [ -z "$REVISION" ]; then
    echo "Sorry. You forgot to mention the revision number!"
    exit 1
fi

if [ ! -d build ]; then
    echo "No build directory available."
    exit 1
fi

echo "Building $APP rpm package ..."

if [ -d build/rpm ]; then
    echo "Removing build/rpm ..."
    rm -rf build/rpm
fi

echo "Creating $APP.tar.gz ..."
mkdir -p build/rpm/$APPVERSION/puppet
pushd $RPM_BUILDROOT
cp ../libs/$APP-$VERSION.war $APPVERSION/$APPVERSION.war
#cp -a ../../src/main/puppet/{modules,manifests} ${APPVERSION}/puppet/
tar cvzf ../$APP.tar.gz $APPVERSION/
popd

echo "Copying $APP.tar.gz ..."
mkdir -p $RPM_BUILDROOT/{SOURCES,BUILD,RPMS,SRPMS}
cp build/$APP.tar.gz $RPM_BUILDROOT/SOURCES

echo "Building rpm ..."
pushd $RPM_BUILDROOT
cp $RPM_SOURCE/$APP.spec.template $APP.spec
sed -i s/@@@REVISION@@@/$REVISION/g $APP.spec
popd

rpmbuild -bb build/rpm/$APP.spec

throw_error $? "RPM build failed"

cp $RPM_BUILDROOT/RPMS/noarch/$APPVERSION-$REVISION.noarch.rpm build
echo "RPM package created."

#ssh root@my-repo.server.org "cd $APP_REPOSITORY && rm -f '${APP}'-*.rpm"
#echo "Deleted old artifacts from ${APP}-repository."

#scp $RPM_BUILDROOT/RPMS/noarch/$APPVERSION-$REVISION.noarch.rpm root@my-repo.server.org:/$APP_REPOSITORY
#throw_error $? "scp of app-rpm to repo-server failed"
#ssh root@my-repo.server.org "createrepo -d $APP_REPOSITORY"
#throw_error $? "update repoindex failed"
#echo "RPM published to ${APP}-repository."